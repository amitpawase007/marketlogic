package com.amit.codetest.suggestion_builder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * @author amit pawase
 * 
 *         the suggestion class provides the suggestion list out of stream. it
 *         also consider the stop words to ignore the word from stream to be
 *         considered. this class doesn't have public constructor to create as
 *         instance. Builder providing facility to create the instance.
 */

public class Suggestion {

	private Suggestion() {
	}

	/**
	 * holds the reference of stop words
	 */
	private List<String> stopWords;
	/**
	 * input stream to suggestion builder
	 */
	private List<String> tokenStream;
	/**
	 * Number of words to be combined in a suggestion
	 */
	private int maxCombinedWords = 3;
	/**
	 * can define the words to be ignored by the length
	 */
	private int maxWordToIgnoreLength = 1;
	/**
	 * character separator for the suggestion tokens
	 */
	private static char SEPARATOR = ' ';

	/**
	 * This method provides the suggestion from the token stream. it considers
	 * the stream with combination for word based {@link #maxCombinedWords}
	 * value. also it ignores the stopwords and break the word combination if
	 * any stopword in between.
	 * 
	 * @return java.util.List of suggestion of type java.lang.String
	 */
	public List<String> suggest() {
		List<String> suggestionList = new ArrayList<String>();
		StringBuilder furtherString;

		for (int streamIndex = 0; streamIndex < tokenStream.size(); streamIndex++) {
			furtherString = new StringBuilder(maxCombinedWords);
			for (int furtherIndex = streamIndex; furtherIndex < (streamIndex + maxCombinedWords)
					&& furtherIndex < tokenStream.size(); furtherIndex++) {
				String part = tokenStream.get(furtherIndex);
				if (checkForInvalidToken(part))
					break;
				furtherString.append(part + SEPARATOR);
				suggestionList.add(furtherString.toString().trim());
			}
		}
		return suggestionList;
	}

	/**
	 * This method provides suggestion by filtering by the keyword
	 * 
	 * @param keyword
	 *            - String type keyword for suggestion
	 * @return java.util.List of suggestion filtered from keyword
	 */
	public List<String> suggestByKeyword(String keyword) {
		return suggest().stream().filter(suggestion -> suggestion.toUpperCase().contains(keyword.toUpperCase()))
				.collect(Collectors.toList());
	}

	/**
	 * This check for the invalid value for null, empty or from stopWords
	 * 
	 * @param part
	 *            - String type token value.
	 * @return - true / false
	 */
	private boolean checkForInvalidToken(String part) {
		return null == part || part.length() <= maxWordToIgnoreLength || stopWords.contains(part.toUpperCase());
	}

	/**
	 * this class builds the suggestion object by taking details the step by
	 * step
	 * 
	 * @author amit pawase
	 *
	 */
	public static class Builder {

		private Suggestion suggestion;

		public Builder(List<String> tokenStream) {
			init(tokenStream);
		}

		/**
		 * Initialization of the suggestion class and the properties associated
		 * with that.
		 * 
		 * @param tokenStream
		 */
		private void init(List<String> tokenStream) {
			// doing eager instantiation to avoid builder temporary variable
			// memory
			suggestion = new Suggestion();
			suggestion.stopWords = new ArrayList<>();
			suggestion.tokenStream = new ArrayList<>();
			if (null != tokenStream && tokenStream.size() > 0) {
				suggestion.tokenStream.addAll(tokenStream);
			}
		}

		/**
		 * Setter for stop words
		 * 
		 * @param stopWords
		 *            - list stop words
		 * @return Self reference
		 */
		public Builder setStopWords(List<String> stopWords) {
			if (null != stopWords && stopWords.size() > 0) {
				stopWords.forEach(word -> suggestion.stopWords.add(word.toUpperCase()));
			}
			return this;
		}

		/**
		 * Setter for max combined words size
		 * 
		 * @param maxCombinedWords
		 * @return Self reference
		 */
		public Builder setMaxCombinedWords(int maxCombinedWords) {
			suggestion.maxCombinedWords = maxCombinedWords;
			return this;
		}

		public Builder setMaxWordToIgnoreLength(int maxWordToIgnoreLength) {
			suggestion.maxWordToIgnoreLength = maxWordToIgnoreLength;
			return this;
		}

		/**
		 * Finally builds the object and return to external world
		 * 
		 * @return - {@link #Suggestion(List)} instance
		 */
		public Suggestion build() {
			return suggestion;
		}

	}

}
