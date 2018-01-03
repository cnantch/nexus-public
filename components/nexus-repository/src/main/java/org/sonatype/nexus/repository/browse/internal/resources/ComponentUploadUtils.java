/*
 * Sonatype Nexus (TM) Open Source Version
 * Copyright (c) 2008-present Sonatype, Inc.
 * All rights reserved. Includes the third-party code listed at http://links.sonatype.com/products/nexus/oss/attributions.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License Version 1.0,
 * which accompanies this distribution and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Sonatype Nexus (TM) Professional Version is available from Sonatype, Inc. "Sonatype" and "Sonatype Nexus" are trademarks
 * of Sonatype, Inc. Apache Maven is a trademark of the Apache Software Foundation. M2eclipse is a trademark of the
 * Eclipse Foundation. All other trademarks are the property of their respective owners.
 */
package org.sonatype.nexus.repository.browse.internal.resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.Nullable;
import javax.ws.rs.core.MediaType;

import org.sonatype.nexus.repository.upload.AssetUpload;
import org.sonatype.nexus.repository.upload.ComponentUpload;
import org.sonatype.nexus.repository.view.PartPayload;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartInput;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;
import static javax.ws.rs.core.HttpHeaders.CONTENT_DISPOSITION;
import static javax.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM_TYPE;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN_TYPE;

/**
 * Utility for processing component upload data.
 *
 * @since 3.next
 */
class ComponentUploadUtils
{
  private static final Pattern FIELD_NAME_PATTERN = Pattern.compile(".*\\sname\\s*=[\'\"\\s]?([^\'\";]+).*");

  private ComponentUploadUtils() {
    // empty
  }

  /**
   * Extract the value from 'name' parameter from the Content Disposition header
   * Example: Content-Disposition: form-data; name="fieldName"; filename="filename.jpg" - will return fieldName
   *
   * @param header Content Disposition header
   * @return value of the 'name' parameter
   */
  private static String extractFieldName(final String header) {
    Matcher matcher = FIELD_NAME_PATTERN.matcher(header);
    matcher.matches();
    return matcher.group(1);
  }

  static ComponentUpload createComponentUpload(final MultipartInput multipartInput) throws IOException
  {
    Map<String, String> textFormFields = getTextFormFields(multipartInput);
    List<AssetUpload> assetUploads = getAssetsPayloads(multipartInput).entrySet().stream()
        .map(asset -> createAssetUpload(asset.getKey(), asset.getValue(), textFormFields))
        .collect(Collectors.toList());

    ComponentUpload componentUpload = new ComponentUpload();
    componentUpload.setFields(getComponentFields(textFormFields, assetUploads));
    componentUpload.setAssetUploads(assetUploads);
    return componentUpload;
  }

  /**
   * Computes component fields by filtering out asset fields out of all form fields.
   */
  private static Map<String, String> getComponentFields(final Map<String, String> formFields,
                                                        final List<AssetUpload> assetUploads)
  {
    Set assetFieldNames = assetUploads.stream()
        .flatMap(asset -> asset.getFields().keySet().stream()
            .map(assetField -> String.format("%s.%s", asset.getPayload().getFieldName(), assetField)))
        .collect(toSet());

    return formFields.entrySet().stream()
        .filter(field -> !assetFieldNames.contains(field.getKey()))
        .collect(toMap(Entry::getKey, Entry::getValue));
  }

  /**
   * Creates an {@link AssetUpload} object with provided {@link InputStreamPartPayload} and fields extracted from the
   * multi-part form. The asset fields must be prefixed with the assetName followed by any delimiter character.
   *
   * @param assetName    Name of the asset
   * @param assetPayload PartPayload of the asset
   * @param formFields   All text fields of the form
   * @return {@link AssetUpload} to be passed to the {@link org.sonatype.nexus.repository.upload.UploadManager}
   */
  private static AssetUpload createAssetUpload(final String assetName,
                                               final InputStreamPartPayload assetPayload,
                                               final Map<String, String> formFields)
  {
    Map<String, String> assetFields = formFields.entrySet().stream()
        .filter(field -> field.getKey().startsWith(assetName))
        .collect(Collectors.toMap(field -> field.getKey().substring(assetName.length() + 1), Entry::getValue));

    AssetUpload assetUpload = new AssetUpload();
    assetUpload.setPayload(assetPayload);
    assetUpload.setFields(assetFields);
    return assetUpload;
  }

  private static List<InputPart> getPartByMediaType(final MultipartInput multipartInput, final MediaType mediaType) {
    return multipartInput.getParts().stream()
        .filter(part -> mediaType.isCompatible(part.getMediaType()))
        .collect(toList());
  }

  private static Map<String, String> getTextFormFields(final MultipartInput multipartInput) throws IOException {
    Map<String, String> fields = new HashMap<>();
    List<InputPart> fieldsParts = getPartByMediaType(multipartInput, TEXT_PLAIN_TYPE);
    for (InputPart inputPart : fieldsParts) {
      String name = extractFieldName(inputPart.getHeaders().getFirst(CONTENT_DISPOSITION));
      fields.put(name, inputPart.getBodyAsString());
    }
    return fields;
  }

  private static Map<String, InputStreamPartPayload> getAssetsPayloads(final MultipartInput multipartInput)
      throws IOException
  {
    Map<String, InputStreamPartPayload> payloads = new HashMap<>();
    List<InputPart> streamParts = getPartByMediaType(multipartInput, APPLICATION_OCTET_STREAM_TYPE);
    for (InputPart inputPart : streamParts) {
      String name = extractFieldName(inputPart.getHeaders().getFirst(CONTENT_DISPOSITION));
      InputStream inputStream = inputPart.getBody(InputStream.class, null);
      payloads.put(name, new InputStreamPartPayload(name, name, inputStream, inputPart.getMediaType().toString()));
    }
    return payloads;
  }

  private static class InputStreamPartPayload
      implements PartPayload
  {

    final String name;

    final String fieldName;

    final InputStream inputStream;

    final String contentType;

    InputStreamPartPayload(final String name,
                           final String fieldName,
                           final InputStream inputStream,
                           final String contentType)
    {
      this.name = name;
      this.fieldName = fieldName;
      this.inputStream = inputStream;
      this.contentType = contentType;
    }

    @Nullable
    @Override
    public String getName() {
      return name;
    }

    @Override
    public String getFieldName() {
      return fieldName;
    }

    @Override
    public boolean isFormField() {
      return false;
    }

    @Override
    public InputStream openInputStream() throws IOException {
      return inputStream;
    }

    @Override
    public long getSize() {
      try {
        return inputStream.available();
      }
      catch (IOException ignored) {
        return 0;
      }
    }

    @Nullable
    @Override
    public String getContentType() {
      return contentType;
    }

    @Override
    public void close() throws IOException {
      inputStream.close();
    }
  }
}
