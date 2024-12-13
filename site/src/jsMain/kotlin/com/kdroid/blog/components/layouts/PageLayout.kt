package com.kdroid.blog.components.layouts

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.ColumnScope
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toAttrs
import com.kdroid.blog.components.sections.Footer
import com.kdroid.blog.components.sections.NavHeader
import kotlinx.browser.document
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*

val CenteredSectionStyle = CssStyle {
    base { Modifier.fillMaxWidth(90.percent) }
    Breakpoint.MD { Modifier.fillMaxWidth(80.percent) }
}

@Composable
fun PageLayout(title: String, description: String = "Tech chatter, tutorials, and career advice", content: @Composable ColumnScope.() -> Unit) {


    LaunchedEffect(title) {
        document.title = "$title"
        document.querySelector("""meta[name="description"]""")!!.setAttribute("content", description)
    }

    Box(Modifier
        .fillMaxWidth()
        .minHeight(100.percent)
        // Create a box with two rows: the main content (fills as much space as it can) and the footer (which reserves
        // space at the bottom). "auto" means the use the height of the row. "1fr" means give the rest of the space to
        // that row. Since this container is set to *at least* 100%, the footer will always appear at least on the
        // bottom but can be pushed further down if the first row (main content) grows beyond the page.
        .gridTemplateRows { size(1.fr); size(auto) }
    , contentAlignment = Alignment.TopCenter) {
        Column(
            // Add some top margin to give some space for where the nav header will appear
            modifier = Modifier.fillMaxSize().maxWidth(1200.px).align(Alignment.TopCenter).margin(top = 4.cssRem),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NavHeader()
            Div(CenteredSectionStyle.toAttrs()) {
                H1 { SpanText(title) }
                content()
            }
        }
        // Associate the footer with the row that will get pushed off the bottom of the page if it can't fit.
        Footer(Modifier.gridRow(2, 3))
    }
}
