val antlr_version: String by project

plugins {
    application
    antlr
}

version = "0.0.1"

dependencies {
    antlr("org.antlr:antlr4:$antlr_version")

    implementation("org.antlr:antlr4-runtime:$antlr_version")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("com.github.shufflezzz.matcher.MainKt")
}

sourceSets {
    main {
        java.srcDir("build/generated-src/antlr/main")
        kotlin.srcDir("src/main")
    }
}

tasks.compileKotlin {
    dependsOn("generateGrammarSource")
}

tasks.generateGrammarSource {
    arguments = arguments + listOf("-visitor", "-long-messages")
}


