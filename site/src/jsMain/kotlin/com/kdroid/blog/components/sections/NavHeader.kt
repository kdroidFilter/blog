package com.kdroid.blog.components.sections

import androidx.compose.runtime.*
import com.kdroid.blog.brand
import com.kdroid.blog.components.widgets.button.ColorModeButton
import com.varabyte.kobweb.browser.dom.ElementTarget
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.functions.blur
import com.varabyte.kobweb.compose.css.functions.saturate
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.icons.fa.FaBars
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.navigation.UndecoratedLinkVariant
import com.varabyte.kobweb.silk.components.overlay.PopupPlacement
import com.varabyte.kobweb.silk.components.overlay.Tooltip
import com.varabyte.kobweb.silk.defer.Deferred
import com.varabyte.kobweb.silk.init.InitSilk
import com.varabyte.kobweb.silk.init.InitSilkContext
import com.varabyte.kobweb.silk.init.registerStyleBase
import com.varabyte.kobweb.silk.style.*
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.common.SmoothColorStyle
import com.varabyte.kobweb.silk.style.selectors.link
import com.varabyte.kobweb.silk.style.selectors.visited
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.varabyte.kobweb.silk.theme.colors.palette.background
import com.varabyte.kobweb.silk.theme.colors.palette.border
import com.varabyte.kobweb.silk.theme.colors.palette.color
import com.varabyte.kobweb.silk.theme.colors.palette.toPalette
import org.jetbrains.compose.web.css.*

@InitSilk
fun initNavHeaderStyles(ctx: InitSilkContext) {
    // Trick to avoid text scrolling under our floating nav header when you click on in-page fragments links like
    // `href="#some-section`.
    // See also: https://developer.mozilla.org/en-US/docs/Web/CSS/scroll-margin-top
    (2..6).forEach { headingLevel ->
        ctx.stylesheet.registerStyleBase("h${headingLevel}") {
            Modifier.scrollMargin(top = 5.cssRem)
        }
    }
}

val NavHeaderStyle = CssStyle.base(extraModifier = { SmoothColorStyle.toModifier() }) {
    Modifier
        .fillMaxWidth()
        .padding(left = 1.cssRem, right = 1.cssRem, top = 1.cssRem, bottom = 1.cssRem)
        .fontSize(1.25.cssRem)
        .position(Position.Fixed)
        .top(0.percent)
        .backgroundColor(colorMode.toPalette().background.toRgb().copyf(alpha = 0.65f))
        .backdropFilter(saturate(180.percent), blur(5.px))
        .borderBottom(width = 1.px, style = LineStyle.Solid, color = colorMode.toPalette().border)

}

sealed interface NavLinkKind : ComponentKind

val NavLinkStyle = CssStyle<NavLinkKind> {
    val linkColor = colorMode.toPalette().color

    base { Modifier.margin(topBottom = 0.px, leftRight = 15.px) }

    link { Modifier.color(linkColor) }
    visited { Modifier.color(linkColor) }
}

val LogoVariant = NavLinkStyle.addVariant {
    val logoColor = colorMode.toPalette().brand

    link { Modifier.color(logoColor) }
    visited { Modifier.color(logoColor) }

}

val NavButtonStyle = CssStyle.base {
    Modifier.margin(0.px, 10.px).backgroundColor(colorMode.toPalette().background)
}

@Composable
private fun NavLink(path: String, text: String, linkVariant: CssStyleVariant<NavLinkKind>? = null) {
    Link(
        path,
        text,
        NavLinkStyle.toModifier(linkVariant),
        UndecoratedLinkVariant,
    )
}

@Composable
fun MenuLinks() {
    NavLink("/blog/", "Last posts")
    NavLink("/android/", "Android")
    NavLink("/kdroidFilter/", "KdroidFilter")
    NavLink("/compose/", "Compose")
    NavLink("/kmp/", "KMP")
    NavLink("/seforim/", "Seforim")


}

@Composable
fun NavHeader(menuOpened: MutableState<Boolean>) {
    val breakpoint = rememberBreakpoint()

    Deferred {
        Row(NavHeaderStyle.toModifier()) {
            val ctx = rememberPageContext()
            if (breakpoint < Breakpoint.MD) {
                FaBars(
                    Modifier
                        .cursor(Cursor.Pointer)
                        .padding(top = 5.px)
                        .color(if (ColorMode.currentState.value.isDark) Color.white else Color.black)
                        .align(Alignment.Top)
                        .onClick {
                            menuOpened.value = !menuOpened.value
                        })
            }
            Column {
                NavLink("/", "K-Droid Dev", LogoVariant)
                if (menuOpened.value) {
                    MenuLinks()
                }
            }
            if (breakpoint > Breakpoint.SM) {
                MenuLinks()
            } else {
                menuOpened.value = false
            }

            Spacer()
            ColorModeButton(NavButtonStyle.toModifier())
            Tooltip(ElementTarget.PreviousSibling, "Toggle color mode", placement = PopupPlacement.BottomRight)

        }

    }
}

