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
package org.sonatype.nexus.common.io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.util.JsonGeneratorDelegate;

import static com.fasterxml.jackson.core.JsonTokenId.ID_FIELD_NAME;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Sanitizes JSON streamed output by replacing named fields with a specified replacement string. For fields containing
 * nested object or array literals, the replacement is performed recursively on all child elements.
 *
 * @since 3.0
 */
public class SanitizingJsonOutputStream
    extends PipedOutputStream
{
  private static final JsonFactory jsonFactory = new JsonFactory();

  private final JsonGenerator generator;

  private final Thread pipe;

  private IOException ioException;

  /**
   * Constructor.
   */
  public SanitizingJsonOutputStream(final OutputStream out, final Collection<String> fields, final String replacement)
      throws IOException
  {
    generator = new SanitizingJsonGenerator(jsonFactory.createGenerator(out), fields, replacement);
    PipedInputStream pipedInput = new PipedInputStream(this);

    pipe = new Thread(() -> {
      try (JsonParser parser = jsonFactory.createParser(pipedInput)) {
        parser.nextToken();
        generator.copyCurrentStructure(parser);
      }
      catch (IOException e) {
        ioException = e;
      }
    });

    pipe.start();
  }

  @Override
  public void close() throws IOException {
    super.close();

    try {
      pipe.join();
    }
    catch (InterruptedException e) {
      throw new IOException(e);
    }
    finally {
      generator.close();
    }

    if (ioException != null) {
      throw ioException;
    }
  }

  /**
   * {@link JsonGeneratorDelegate} that performs the actual replacement of fields (and nested fields).
   */
  private static class SanitizingJsonGenerator
      extends JsonGeneratorDelegate
  {
    private final Set<String> fields;

    private final String replacement;

    private int skip;

    /**
     * Constructor.
     */
    public SanitizingJsonGenerator(final JsonGenerator delegate,
                                   final Collection<String> fields,
                                   final String replacement)
    {
      super(delegate, false);
      this.fields = new HashSet<>(fields);
      this.replacement = checkNotNull(replacement);
    }

    @Override
    public void copyCurrentStructure(JsonParser jp) throws IOException {
      if (jp.hasTokenId(ID_FIELD_NAME) && fields.contains(jp.getCurrentName())) {
        skip++;
        super.copyCurrentStructure(jp);
        skip--;
      }
      else {
        super.copyCurrentStructure(jp);
      }
    }

    @Override
    public void copyCurrentEvent(JsonParser jp) throws IOException {
      if (skip > 0) {
        writeString(replacement);
      }
      else {
        super.copyCurrentEvent(jp);
      }
    }
  }
}
