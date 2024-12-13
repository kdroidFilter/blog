package com.kdroid.blog.components.widgets.blog

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.FontStyle
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.selectors.after
import com.varabyte.kobweb.silk.style.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.palette.border
import com.varabyte.kobweb.silk.theme.colors.palette.toPalette
import com.kdroid.blog.components.widgets.date.DateText
import com.kdroid.blog.components.widgets.dom.NoListIndentationModifier
import com.kdroid.blog.components.widgets.dom.StyledDiv
import com.kdroid.blog.components.widgets.dom.StyledSpan
import com.varabyte.kobweb.silk.style.selectors.before
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*

val ArticleListStyle = CssStyle.base {
    NoListIndentationModifier
}

val ArticleSectionStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .margin(top = 1.5.cssRem)
        .padding(1.cssRem)
        .border(1.px, LineStyle.Solid, colorMode.toPalette().border)
        .borderRadius(5.px)
}

val ArticleTitleStyle = CssStyle.base {
    Modifier.fontWeight(FontWeight.Bold)
}

val ArticleMetaStyle = CssStyle.base {
    Modifier.opacity(0.6)
}

val ArticleAuthorStyle = CssStyle.base {
    Modifier
}

val ArticleDateStyle = CssStyle {
    after {
        Modifier.content(" • ")
    }
}

val ArticleNameStyle = CssStyle.base {
    Modifier
}

val ArticleUpdatedStyle = CssStyle.base {
    Modifier.fontStyle(FontStyle.Italic)
}

val ArticleDescStyle = CssStyle.base {
    Modifier.margin(top = 0.3.cssRem)
}

val ArticleCategoryStyle = CssStyle {
    before {
        Modifier.content(" • ")
    }
}


class ArticleEntry(val path: String, val author: String, val date: String, val title: String, val desc: String, val category: String)

@Composable
fun ArticleList(entries: List<ArticleEntry>) {
    Ul(ArticleListStyle.toAttrs()) {
        entries.forEach { entry ->
            Li {
                ArticleSummary(entry)
            }
        }
    }
}

@Composable
fun AuthorDateCategory(author: String, date: String, updated: String? = null, category: String, modifier: Modifier = Modifier) {
    Div(attrs = ArticleMetaStyle.toModifier().then(modifier).toAttrs()) {
        StyledSpan(ArticleDateStyle) {
            DateText(date)
        }
        StyledSpan(ArticleAuthorStyle) { SpanText(author) }
        StyledSpan(ArticleCategoryStyle) { SpanText(category) }
        if (updated != null) {
            StyledSpan(ArticleUpdatedStyle) {
                Br()
                SpanText("Updated ")
                DateText(updated)
            }
        }
    }
}

@Composable
private fun ArticleSummary(entry: ArticleEntry) {
    StyledDiv(ArticleSectionStyle) {
        StyledDiv(ArticleTitleStyle) { Link(entry.path, entry.title) }
        AuthorDateCategory(entry.author, entry.date, category = entry.category)
        StyledDiv(ArticleDescStyle) {
            SpanText(entry.desc)
        }
    }
}
