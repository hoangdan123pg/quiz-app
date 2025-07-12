package com.example.project_quiz_app.model;

public class FlashcardItem {
        private int id;
        private String term;
        private String definition;

        public FlashcardItem(int id,String term, String definition) {
            this.id = id;
            this.term = term;
            this.definition = definition;
        }
        public int getId() {
            return id;
        }
        public void setId(int id) {
            this.id = id;
        }

        public String getTerm() {
            return term;
        }

        public String getDefinition() {
            return definition;
        }
    }

