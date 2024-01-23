package app

import dev.fritz2.core.*
import dev.fritz2.headless.components.popOver
import dev.fritz2.headless.foundation.portalRoot
import dev.fritz2.headless.foundation.utils.floatingui.core.middleware.offset
import dev.fritz2.headless.foundation.utils.floatingui.utils.PlacementValues
import kotlinx.browser.document
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.map
import org.w3c.dom.HTMLInputElement

fun main() {
    val selectedValue = storeOf("", job = Job())
    val values = listOf("Strawburry", "Cherry", "Banana", "Apple", "Pear")

    render {
        br {  }
        popOver {
            div("form-floating") {
                popOverButton("form-control border border-2", tag = RenderContext::input) {
                    id("searchFruitInput")
                    type("text")
                    placeholder("Search a fruit")
                    value(selectedValue.data)
                    clicks handledBy toggle
                }

                label("form-label") {
                    `for`("searchFruitInput")
                    +"Search a fruit"
                }
            }

            popOverPanel("col-md-4") {
                placement = PlacementValues.bottomStart
                addMiddleware(offset(10))
                div("col-md-8") {
                    div("dropdown-menu") {
                        attr(
                            "style", """
                    |display: block;
                    |width: ${(document.getElementById("searchFruitInput") as HTMLInputElement).offsetWidth}px;
                    |""".trimMargin()
                        )
                        ul("list-unstyled mb-0") {
                            values.forEach { entry ->
                                li {
                                    a("dropdown-item d-flex align-items-center gap-2 py-2") {
                                        href("#")
                                        span("select-item") {
                                            +entry
                                        }
                                        clicks.map { entry } handledBy selectedValue.update
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        portalRoot()
    }
}
