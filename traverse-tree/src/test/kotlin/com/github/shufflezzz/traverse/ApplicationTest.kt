package com.github.shufflezzz.traverse

import com.github.shufflezzz.traverse.dtos.Tree
import com.github.shufflezzz.traverse.dtos.TreeDepthRequest
import com.github.shufflezzz.traverse.dtos.TreeDepthResponse
import com.github.shufflezzz.traverse.plugins.configureKoin
import com.github.shufflezzz.traverse.plugins.configureRouting
import com.github.shufflezzz.traverse.plugins.configureSerialization
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.After
import org.junit.Test
import org.koin.core.context.stopKoin
import kotlin.test.assertEquals


class ApplicationTest {

    private val sampleInput = this::class.java.classLoader.getResource("sample.json").readText()

    @Test
    fun sampleTest() = testTreeDepth(sampleInput, 3)

    @Test
    fun noHeaderTest() =
        testTreeDepth(sampleInput, expectedCode = HttpStatusCode.UnsupportedMediaType, headers = emptyMap())

    @Test
    fun wrongJsonTest() =
        testTreeDepth(Json.encodeToString(TreeDepthResponse(3)), expectedCode = HttpStatusCode.BadRequest)

    @Test
    fun nullTest() = testTreeDepth("null", expectedCode = HttpStatusCode.BadRequest)

    @Test
    fun singleNodeTest() = testTreeDepth(Tree("A"), 1)

    @Test
    fun balancedTreeTest() = testTreeDepth(
        Tree(
            "A",
            Tree(
                "B",
                Tree("D"),
                Tree("E"),
            ),
            Tree(
                "C",
                Tree("F"),
                Tree("G"),
            ),
        ), 3
    )

    @Test
    fun middleBranchTest() = testTreeDepth(
        Tree(
            "A",
            Tree(
                "B",
                null,
                Tree(
                    "C",
                    Tree("D"),
                    Tree(
                        "E",
                        Tree("F"),
                    ),
                ),
            ),
        ), 5
    )

    @Test
    fun bambooTest() = testTreeDepth(
        Tree(
            "A",
            Tree(
                "B",
                Tree(
                    "C",
                    Tree(
                        "D",
                        Tree("E"),
                    ),
                ),
            ),
        ), 5
    )

    @After
    fun after() {
        stopKoin()
    }

    private fun testTreeDepth(
        json: String,
        expectedResponse: Int? = null,
        expectedCode: HttpStatusCode = HttpStatusCode.OK,
        headers: Map<String, String> = mapOf(HttpHeaders.ContentType to "application/json"),
    ) = testApplication {
        application {
            configureKoin()
            configureRouting()
            configureSerialization()
        }

        val response = client.post("api/v1/tree-depth") {
            headers.forEach { (k, v) -> header(k, v) }
            setBody(json)
        }

        assertEquals(expectedCode, response.status)

        if (expectedResponse != null) {
            val responseBodyTyped = Json.decodeFromString(TreeDepthResponse.serializer(), response.bodyAsText())
            assertEquals(TreeDepthResponse(expectedResponse), responseBodyTyped)
        }
    }

    private fun testTreeDepth(
        tree: Tree,
        expectedResponse: Int? = null,
        expectedCode: HttpStatusCode = HttpStatusCode.OK,
        headers: Map<String, String> = mapOf(HttpHeaders.ContentType to "application/json"),
    ) = testTreeDepth(Json.encodeToString(TreeDepthRequest(tree)), expectedResponse, expectedCode, headers)
}


