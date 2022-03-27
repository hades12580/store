package com.cy.doc;

import io.github.yedaxia.apidocs.Docs;
import io.github.yedaxia.apidocs.DocsConfig;

/**
 * @author XuBowen
 * @version 1.0
 * @date 2022/3/25 21:34
 */


public class apiDocGenerate {
    public static void main(String[] args) {
        DocsConfig config = new DocsConfig();
        config.setProjectPath("D:\\JavaProject\\store"); // 项目根目录
        config.setProjectName("store");                  // 项目名称
        config.setApiVersion("V1.0");                    // 声明该API的版本
        config.setDocsPath("D:\\JavaProject\\store\\src\\main\\java\\com\\cy\\doc"); // 生成API 文档所在目录
        config.setAutoGenerate(Boolean.TRUE);            // 配置自动生成
        Docs.buildHtmlDocs(config);                      // 执行生成文档
    }
}
