---
root: .components.layouts.BlogLayout
title: "Conception de la Base de Données pour K-Droid Seforim"
description: "Un aperçu approfondi du processus de conception de la base de données pour K-Droid Seforim, en mettant l'accent sur l'efficacité, la synchronisation et la fonctionnalité hors ligne."
author: Elyahou Hadass
date: 2024-12-15
tags:
  - kotlin-multiplatform
  - sefaria
  - database-design
category: "Seforim"
---

# Conception de la Base de Données

Bonjour à tous,

Aujourd’hui, nous allons nous attaquer à la partie la plus complexe du projet K-Droid Seforim : **la base de données** !

## Clarifions nos Objectifs

Avant de plonger dans les aspects techniques, voici les objectifs que nous souhaitons atteindre avec la base de données :

- **Facilité d’utilisation** : La base de données doit être simple à utiliser et facile à gérer.
- **Accès rapide** : Même sur des appareils peu puissants, l’accès aux données doit être rapide.
- **Légèreté** : La base de données ne doit pas être trop lourde à télécharger.
- **Synchronisation incrémentale** : Les utilisateurs doivent pouvoir synchroniser les modifications sans avoir à retélécharger l’intégralité de la base de données.
- **Mises à jour de Sefaria** : Synchronisation avec la base de données de Sefaria pour obtenir les derniers contenus.

## Solution Proposée

Après réflexion, j’ai décidé que la meilleure approche consiste à créer une **base de données parallèle** à celle de Sefaria, en exploitant leur API. Voici comment cela fonctionnerait :

1. **Stockage local des appels API** :
    - L’idée est de stocker localement toutes les réponses potentielles des appels API.
    - Les données seraient organisées de manière hiérarchique, permettant un accès structuré.

2. **Imitation du comportement de l’API** :
    - Bien que les données stockées localement ne soient pas intrinsèquement liées, interroger la base de données simulerait un appel à l’API de Sefaria. Cependant, tout se ferait localement.

3. **Mises à jour efficaces** :
    - En structurant les données de manière hiérarchique, les mises à jour peuvent être synchronisées de façon granulaire, évitant ainsi de devoir retélécharger l’ensemble des données.

## Inconvénient Majeur

Un inconvénient majeur se trouve lorsque l'on va devoir rechercher quelque chose dans cette base de données. Nous verrons comment régler ce problème dans la section suivante sur le moteur de recherche, mais effectivement, nous allons devoir établir notre propre moteur de recherche pour réduire au maximum ce problème.

## Avantages de cette Approche

- **Accès hors ligne** : Toutes les données sont disponibles hors ligne, rendant l’application totalement fonctionnelle sans connexion Internet.
- **Amélioration des performances** : Les appels locaux sont nettement plus rapides que les requêtes API distantes.
- **Flexibilité** : La structure hiérarchique facilite l’évolution et l’intégration de nouvelles fonctionnalités.
- **Synchronisation fluide** : Les mises à jour incrémentales réduisent l’utilisation de la bande passante et améliorent l’expérience utilisateur.

## Les Étapes Principales

### 1. Récupération des Informations Nécessaires

La première étape consiste à obtenir la liste de tous les livres classés de façon hiérarchique. Pour cela, Sefaria fournit un exemple d'appel à l'API avec `HttpClient`, mais comme précédemment mentionné, nous utiliserons `Ktor` :

```kotlin
val client = OkHttpClient()

val request = Request.Builder()
  .url("https://www.sefaria.org/api/index/")
  .get()
  .addHeader("accept", "application/json")
  .build()

val response = client.newCall(request).execute()
```

Cette requête permet de récupérer les informations nécessaires sur tous les livres disponibles dans la base de données de Sefaria.

Cependant, cette requête retourne un JSON qui peut atteindre **50 000 lignes**. Pour mieux comprendre la structure des données, examinons les 100 premières lignes du résultat. Voici un extrait typique :

```json
[
  {
    "contents": [
      {
        "categories": ["Tanakh", "Torah"],
        "order": 1,
        "primary_category": "Tanakh",
        "enShortDesc": "Creation, the beginning of mankind, and stories of the patriarchs and matriarchs.",
        "heShortDesc": "בריאת העולם, תחילתה של האנושות וסיפורי האבות והאמהות.",
        "heTitle": "בראשית",
        "title": "Genesis"
      },
      {
        "categories": ["Tanakh", "Torah"],
        "order": 2,
        "primary_category": "Tanakh",
        "enShortDesc": "The Israelites’ enslavement in Egypt, miraculous redemption, the giving of the Torah, and building of the Mishkan (Tabernacle).",
        "heShortDesc": "שעבוד בני ישראל במצרים, יציאת מצרים, מתן תורה ובניית המשכן.",
        "heTitle": "שמות",
        "title": "Exodus"
      }
    ]
  }
]
```

Ce JSON illustre comment les livres sont classés par catégorie, titre, et descriptions. À partir de ce JSON, on voit qu'il va être assez simple de créer un arbre hiérarchique des livres.

### Structuration des Données en Kotlin

Pour mieux organiser et manipuler ces données, nous pouvons commencer par structurer notre modèle de données en Kotlin en utilisant la librairie `kotlinx.serialization` :

```kotlin
@Serializable
data class TableOfContent(
    @SerialName("contents") val contents: List<ContentItem> = emptyList(),
    @SerialName("order") var order: Double? = null,
    @SerialName("enComplete") val enComplete: Boolean? = null,
    @SerialName("heComplete") val heComplete: Boolean? = null,
    @SerialName("enDesc") val enDesc: String? = null,
    @SerialName("heDesc") val heDesc: String? = null,
    @SerialName("enShortDesc") val enShortDesc: String? = null,
    @SerialName("heShortDesc") val heShortDesc: String? = null,
    @SerialName("heCategory") val heCategory: String? = null,
    @SerialName("category") val category: String? = null
)

@Serializable
data class ContentItem(
    @SerialName("contents") val contents: List<ContentItem>? = null,
    @SerialName("categories") val categories: List<String>? = null,
    @SerialName("order") var order: Double? = null,
    @SerialName("primary_category") val primaryCategory: String? = null,
    @SerialName("enShortDesc") val enShortDesc: String? = null,
    @SerialName("heShortDesc") val heShortDesc: String? = null,
    @SerialName("corpus") val corpus: String? = null,
    @SerialName("heTitle") val heTitle: String? = null,
    @SerialName("title") val title: String? = null,
    @SerialName("enComplete") val enComplete: Boolean? = null,
    @SerialName("heComplete") val heComplete: Boolean? = null,
    @SerialName("enDesc") val enDesc: String? = null,
    @SerialName("heDesc") val heDesc: String? = null,
    @SerialName("heCategory") val heCategory: String? = null,
    @SerialName("category") val category: String? = null
)
```

Cette structure reflète le JSON retourné par l'API de Sefaria, ce qui facilite la gestion des données hiérarchiques.

### Affichage Hiérarchique avec Jewel

Nous pouvons déjà les afficher sous forme d’arbre hiérarchique en utilisant Jewel :

```kotlin
fun convertToTree(data: List<TableOfContent>): Tree<String> {
    return buildTree {
        data.forEach { toc ->
            addNode(toc.heCategory ?: "Uncategorized") {
                buildContentTree(this, toc.contents)
            }
        }
    }
}

fun buildContentTree(treeBuilder: ChildrenGeneratorScope<String>, contents: List<ContentItem>?) {
    contents?.forEach { content ->
        val nodeName = content.heTitle ?: content.heCategory ?: "Untitled"
        if (content.contents.isNullOrEmpty()) {
            // Dernier enfant : utiliser addLeaf
            treeBuilder.addLeaf(nodeName)
        } else {
            // Nœud intermédiaire : utiliser addNode
            treeBuilder.addNode(nodeName) {
                buildContentTree(this, content.contents)
            }
        }
    }
}

suspend fun fetchTableOfContents(): List<TableOfContent> {
    val client = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }
    return client.get("https://www.sefaria.org/api/index/").body()
}


@OptIn(ExperimentalJewelApi::class)
@Composable
fun DisplayTree() {
    var tree by remember { mutableStateOf<Tree<String>>(emptyTree()) }

    LaunchedEffect(Unit) {
        val data = fetchTableOfContents()
        tree = convertToTree(data)
    }

    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyTree(
            tree = tree,
            modifier = Modifier.fillMaxSize(),
            onElementClick = { element ->
                println("Clicked on: \${element.data}")
            },
            onElementDoubleClick = { element ->
                println("Double clicked on: \${element.data}")
            }
        ) { element ->
            Box(Modifier.fillMaxWidth().padding(4.dp)) {
                Text(element.data, Modifier.padding(2.dp))
            }
        }
    }
}
```
Ce code permet de transformer les données structurées en un arbre affichable et interactif. Jewel offre une interface élégante et performante pour naviguer dans les données hiérarchiques. On remarquera toutefois que le chevron est orienté vers la gauche. Pour corriger ce comportement, il est nécessaire d’utiliser la méthode décrite dans [cette discussion](https://github.com/JetBrains/jewel/pull/721#issuecomment-2535390066).


### 2. Nettoyage des Données

Une fois les informations récupérées, il sera crucial de supprimer toutes les données inutiles ou redondantes pour optimiser le stockage et l'accès futur.

### 3. Formatage et Compression


Enfin, les données seront formatées pour s'adapter à la structure de la base de données locale et compressées pour minimiser leur poids tout en maintenant des performances optimales.

