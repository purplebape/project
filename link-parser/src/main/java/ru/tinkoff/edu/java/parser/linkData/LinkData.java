package ru.tinkoff.edu.java.parser.linkData;

public sealed interface LinkData permits
    GitHubLinkData,
    StackOverflowLinkData {
}
