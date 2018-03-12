package com.amit.codetest.suggestion_builder;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class SuggestionTest {

	private static List<String> tokenStream;

	private static List<String> stopWords;

	@BeforeClass
	public static void initOnce() {
		tokenStream = Arrays.asList("The", "beautiful", "girl", "from", "the", "farmers", "market", ".", "I", "like",
				"chewing", "gum", ".");
		stopWords = Arrays.asList("is", "a", "can", "the");
	}

	@Test
	public void checkSuggestionBuilderForNotNull() {
		Suggestion sugg = new Suggestion.Builder(null).build();
		Assert.assertNotNull(sugg);
	}

	@Test
	public void checkSuggestionBuilderForEmptySuggestion() {
		Suggestion sugg = new Suggestion.Builder(null).build();
		List<String> suggest = sugg.suggest();
		Assert.assertNotNull(suggest);
		Assert.assertEquals(0, suggest.size());
	}

	@Test
	public void checkSuggestionBuilderForSuggestion() {
		Suggestion sugg = new Suggestion.Builder(stopWords).build();
		List<String> suggest = sugg.suggest();
		Assert.assertNotNull(suggest);
		Assert.assertEquals(4, suggest.size());
		Assert.assertTrue(suggest.contains("can the"));
		Assert.assertFalse(suggest.contains("a"));
	}

	@Test
	public void checkSuggestionBuilderForSuggestionWithStopWorld() {
		Suggestion sugg = new Suggestion.Builder(tokenStream).setStopWords(stopWords).build();
		List<String> suggest = sugg.suggest();
		Assert.assertNotNull(suggest);
		Assert.assertEquals(15, suggest.size());
		Assert.assertTrue(suggest.contains("beautiful girl from"));
	}

	@Test
	public void checkSuggestionBuilderForSuggestionByKeywordWithStopWorld() {
		Suggestion sugg = new Suggestion.Builder(tokenStream).setStopWords(stopWords).build();
		List<String> suggest = sugg.suggestByKeyword("girl");
		Assert.assertNotNull(suggest);
		Assert.assertEquals(4, suggest.size());
	}

	@Test
	public void checkSuggestionBuilderForSuggestionWithMaxCombinedWords() {
		Suggestion sugg = new Suggestion.Builder(tokenStream).setMaxCombinedWords(5).build();
		List<String> suggest = sugg.suggestByKeyword("girl");
		Assert.assertNotNull(suggest);
		Assert.assertEquals(12, suggest.size());
	}

	@Test
	public void checkSuggestionBuilderForSuggestionWithMaxWordToIgnoreLength() {
		Suggestion sugg = new Suggestion.Builder(tokenStream).setStopWords(stopWords).setMaxWordToIgnoreLength(5)
				.build();
		List<String> suggest = sugg.suggest();
		Assert.assertNotNull(suggest);
		Assert.assertEquals(5, suggest.size());
		Assert.assertFalse(suggest.contains("girl"));
	}

}
