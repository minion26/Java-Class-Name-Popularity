package org.example
import java.io.File

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun runPythonScript(scriptPath: String) {
    val processBuilder = ProcessBuilder("python", scriptPath)
    val process = processBuilder.start()
    val result = process.inputStream.bufferedReader().readText()
    println(result)
}



// Custom tokenizer function
fun customTokenizer(text: String): List<String> {
    val regex = Regex("[A-Z][a-z]*|[a-z]+|[0-9]+|_")
    return regex.findAll(text).map { it.value }.toList()
}




fun main() {
//    runPythonScript("scripts\\get_repos.py")
//    runPythonScript("scripts\\get_files_from_repos.py")
//    runPythonScript("scripts\\slice_names.py")


    // Read file names
    val names = File("sliced_names_from_repo2.txt").readLines(Charsets.UTF_8)

    // Tokenize names
    val allTokens = mutableListOf<String>()
    for (name in names) {
        val tokens = customTokenizer(name)
        allTokens.addAll(tokens)
    }

    // Count word frequencies
    val wordCounts = allTokens.groupingBy { it }.eachCount()

    // Sort by frequency
    val sortedWordCounts = wordCounts.entries.sortedByDescending { it.value }

    // Save to a file
    File("word_popularity_ranking2.txt").bufferedWriter(Charsets.UTF_8).use { writer ->
        for ((word, count) in sortedWordCounts) {
            writer.write("$word: $count\n")
        }
    }

    println("Word popularity ranking saved to word_popularity_ranking2.txt")


    runPythonScript("scripts\\kmeans-elbow-points.py")
    runPythonScript("scripts\\kmeans.py")
}