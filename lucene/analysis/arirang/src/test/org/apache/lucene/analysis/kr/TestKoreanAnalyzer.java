package org.apache.lucene.analysis.kr;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.BaseTokenStreamTestCase;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;

public class TestKoreanAnalyzer extends BaseTokenStreamTestCase {


  public void testBasics() throws IOException {
    assertAnalyzesTo(new KoreanAnalyzer(TEST_VERSION_CURRENT), "자바로 전부 제작된 텍스트 검색 엔진 라이브러리",
        new String[]{"자바로", "자바", "전부", "제작된", "제작", "텍스트", "검색", "엔진", "라이브러리"},
        new int[]{0, 0, 4, 7, 7, 11, 15, 18, 21},
        new int[]{3, 2, 6, 10, 9, 14, 17, 20, 26},
        new int[]{1, 0, 1, 1, 0, 1, 1, 1, 1}
    );

  }

  public void testOutput() throws IOException {
    String korean = "자바로 전부 제작된 텍스트 검색 엔진 라이브러리";
    Analyzer analyzer = new KoreanAnalyzer(TEST_VERSION_CURRENT);

    TokenStream stream = analyzer.tokenStream("dummy", new StringReader(korean));
    stream.reset();

    CharTermAttribute termAttr = stream.addAttribute(CharTermAttribute.class);
    OffsetAttribute offsetAttr = stream.addAttribute(OffsetAttribute.class);
    TypeAttribute typeAttr = stream.addAttribute(TypeAttribute.class);
    PositionIncrementAttribute positionAttr = stream.addAttribute(PositionIncrementAttribute.class);

    while (stream.incrementToken()) {

      System.out.println(
          "term: " + termAttr +
              "\ttype: " + typeAttr.type() +
              "\tstart offset: " + offsetAttr.startOffset() +
              "\tend offset: " + offsetAttr.endOffset() +
              "\tposition increment: " + positionAttr.getPositionIncrement()
      );
    }
  }
}
