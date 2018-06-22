package edu.nyu.aco_tests;

public class Query {
    String browser;
    String field;
    String scope;
    String query;
    int minNumResults;

    Query(String field, String scope, String query, int minNumResults, String browser){
        this.field = field;
        this.scope = scope;
        this.query = query;
        this.minNumResults = minNumResults;
        this.browser = browser;
    }

    @Override
    public String toString(){
        return String.format("SearchTest: %s\nField: %s\nScope: %s\nNumber: %d", this.query, this.field, this.scope, this.minNumResults);
    }
}
