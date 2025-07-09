package com.example.project_quiz_app.model;

public class FlashcardItem {
        private String term;
        private String definition;

        public FlashcardItem(String term, String definition) {
            this.term = term;
            this.definition = definition;
        }

        public String getTerm() {
            return term;
        }

        public String getDefinition() {
            return definition;
        }
    }

