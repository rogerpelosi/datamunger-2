package com.stackroute.datamunger.query.parser;

/*There are total 4 DataMungerTest file:
 * 
 * 1)DataMungerTestTask1.java file is for testing following 4 methods
 * a)getBaseQuery()  b)getFileName()  c)getOrderByClause()  d)getGroupByFields()
 * 
 * Once you implement the above 4 methods,run DataMungerTestTask1.java
 * 
 * 2)DataMungerTestTask2.java file is for testing following 2 methods
 * a)getFields() b) getAggregateFunctions()
 * 
 * Once you implement the above 2 methods,run DataMungerTestTask2.java
 * 
 * 3)DataMungerTestTask3.java file is for testing following 2 methods
 * a)getRestrictions()  b)getLogicalOperators()
 * 
 * Once you implement the above 2 methods,run DataMungerTestTask3.java
 * 
 * Once you implement all the methods run DataMungerTest.java.This test case consist of all
 * the test cases together.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class QueryParser {

	//does the below line allow us to basically set and get the variables in QParam?
		//ie queryParamater.getFileName() --> is that its purpose?
	private QueryParameter queryParameter = new QueryParameter();

	/*
	 * This method will parse the queryString and will return the object of
	 * QueryParameter class
	 */
	public QueryParameter parseQuery(String queryString) {
		//ensures everything is lower case for ease down the line
		String lowerCase = queryString.toLowerCase();

		//calling the setFileName setter in qPara class and passing logicMethod which takes in lower cased string
		queryParameter.setFileName(getFileNameLogic(lowerCase));
		queryParameter.setBaseQuery(getBaseQueryLogic(lowerCase));
		queryParameter.setGroupByFields(getGroupByFieldsLogic(lowerCase));
		queryParameter.setOrderByFields(getOrderByFieldsLogic(lowerCase));
		queryParameter.setFields(getFieldsLogic(lowerCase));
		queryParameter.setRestrictions(getRestrictionsLogic(queryString));
		queryParameter.setLogicalOperators(getLogicalOperatorsLogic(queryString));
		queryParameter.setAggregateFunctions(getAggregateFunctionsLogic(queryString));

		return queryParameter;
	}

	/*
	 * Extract the name of the file from the query. File name can be found after the
	 * "from" clause.
	 */
	public String getFileNameLogic(String qString){
		//splits at from
		//take the second element which contains everything after from (including file name)
		//then split this element by spaces
		//take the second element of this bc there is a space before the filename presumably
		return qString.split("from")[1].split(" ")[1];
	}

	/*
	 * 
	 * Extract the baseQuery from the query.This method is used to extract the
	 * baseQuery from the query string. BaseQuery contains from the beginning of the
	 * query till the where clause
	 */
	public String getBaseQueryLogic(String qString){
		return qString.split("where")[0].trim();
	}

	/*
	 * extract the order by fields from the query string. Please note that we will
	 * need to extract the field(s) after "order by" clause in the query, if at all
	 * the order by clause exists. For eg: select city,winner,team1,team2 from
	 * data/ipl.csv order by city from the query mentioned above, we need to extract
	 * "city". Please note that we can have more than one order by fields.
	 */
	public List<String> getOrderByFieldsLogic(String qString){
		List<String> orderFields = new ArrayList<>();

		if(qString.contains("order by")){
			String[] splitOrderArr = qString.split("order by");
			for(int i = 0; i < splitOrderArr.length; i++){
				if(i > 0)
				{
					orderFields.add(splitOrderArr[i].trim());
				}
			}
			return orderFields;
		}

		return null;
	}

	/*
	 * Extract the group by fields from the query string. Please note that we will
	 * need to extract the field(s) after "group by" clause in the query, if at all
	 * the group by clause exists. For eg: select city,max(win_by_runs) from
	 * data/ipl.csv group by city from the query mentioned above, we need to extract
	 * "city". Please note that we can have more than one group by fields.
	 */
	public List<String> getGroupByFieldsLogic(String qString){
		//System.out.println("GroupBy Clause Logic Triggered " + qString);
		List<String> groupFields = new ArrayList<>();
		//split by order by, then take that first index (0) and split by group by
		//select winner,season,team2 from ipl.csv where season > 2014 group by winner order by team1
		//String[] splitOrderArr = qString.split("order by");
		//{select winner,season,team2 from ipl.csv where season > 2014 group by winner | team1}
		//String[] splitGroupArr = splitOrderArr[0].split("group by");
		//{select winner,season,team2 from ipl.csv where season > 2014 | winner}
//		System.out.println(qString);
		//loops thru array, only take after index 0 bc that is where group by fields are
		if(qString.contains("order by")){
			String[] splitOrderArr = qString.split("order by");
			if(splitOrderArr[0].contains("group by")){
				String[] splitGroupArr = splitOrderArr[0].split("group by");
				for(int i = 0; i < splitGroupArr.length; i++){
					if(i > 0)
					{
						//{"winner"}
						groupFields.add(splitGroupArr[i].trim());
					}
				}
				return groupFields;
			}
		}
		else if(qString.contains("group by")){
			String[] splitGroupArr = qString.split("group by");
			for(int i = 0; i < splitGroupArr.length; i++){
				if(i > 0)
				{
					//{"winner"}
					groupFields.add(splitGroupArr[i].trim());
				}
			}
			return groupFields;
		}

		return null;

	}

	/*
	 * Extract the selected fields from the query string. Please note that we will
	 * need to extract the field(s) after "select" clause followed by a space from
	 * the query string. For eg: select city,win_by_runs from data/ipl.csv from the
	 * query mentioned above, we need to extract "city" and "win_by_runs". Please
	 * note that we might have a field containing name "from_date" or "from_hrs".
	 * Hence, consider this while parsing.
	 */
	public List<String> getFieldsLogic(String qString){
		List<String> fields = new ArrayList<>();
		//str = "select winner,season,team2 from ipl.csv where season > 2014 group by winner"
		String[] selectArr = qString.split("select ");
		//{"" | winner,season,team2 from ipl.csv where season > 2014 group by winner}
		String[] fromArr = selectArr[1].split("from");
		//{winner,season,team2 | ipl.csv where season > 2014 group by winner}
		String[] fieldsOnlyArr = fromArr[0].split(",");
		//{winner | season | team2}

		for(String item: fieldsOnlyArr){
			fields.add(item.trim());
		}
		return fields;
	}

	/*
	 * Extract the conditions from the query string(if exists). for each condition,
	 * we need to capture the following: 1. Name of field 2. condition 3. value
	 * 
	 * For eg: select city,winner,team1,team2,player_of_match from data/ipl.csv
	 * where season >= 2008 or toss_decision != bat
	 * 
	 * here, for the first condition, "season>=2008" we need to capture: 1. Name of
	 * field: season 2. condition: >= 3. value: 2008
	 * 
	 * the query might contain multiple conditions separated by OR/AND operators.
	 * Please consider this while parsing the conditions.
	 * 
	 */
	public List<Restriction> getRestrictionsLogic(String qString) {
		List<Restriction> restrictionsList = new ArrayList<>();
		String[] symbols = {"=", "<", ">", "!=", "<=", ">="};

		//"select winner,season,team1,team2 from ipl.csv where season = 2014 and city ='bangalore'"
		//String[] whereSplitArr = qString.split("where ");
		//{select winner,season,team1,team2 from ipl.csv | season = 2014 and city ='bangalore'}
		//String[] andOrSplitArr = whereSplitArr[1].split(" and | or ");
		//{season = 2014 | city ='bangalore'}
		if(qString.contains(" where ")){
			String[] whereSplitArr = qString.split("where ");
			String[] andOrSplitArr = whereSplitArr[1].split(" and | or ");
			if (andOrSplitArr.length == 1) {
				String singleRestrict = andOrSplitArr[0];
				for (String symbol : symbols) {
					if (singleRestrict.contains(symbol)) {
						String[] conditionSplit = singleRestrict.trim().split(symbol);
						restrictionsList.add(new Restriction(conditionSplit[0].trim(), conditionSplit[1].trim(), symbol));
					}
				}
			} else if (andOrSplitArr.length > 1) {
				for (String oneConditionOfMultiple : andOrSplitArr) {
					for (String symbol : symbols) {
						if (oneConditionOfMultiple.contains(symbol)) {
							String[] conditionSplit = oneConditionOfMultiple.trim().split(symbol);
							String selectedValue = conditionSplit[1];
							if (!selectedValue.contains("'")) {
								restrictionsList.add(new Restriction(conditionSplit[0].trim(), conditionSplit[1].trim(), symbol));
							} else if (selectedValue.contains("'")) {
								String[] quoteSplit = selectedValue.split("'");

								restrictionsList.add(new Restriction(conditionSplit[0].trim(), quoteSplit[1], symbol));
							}
						}
					}
				}
			}
			return restrictionsList;
		}
		//can have multiple restrictions
		//create a list of each restriction object
		//return a list of restrictions
		return null;
	}

	/*
	 * Extract the logical operators(AND/OR) from the query, if at all it is
	 * present. For eg: select city,winner,team1,team2,player_of_match from
	 * data/ipl.csv where season >= 2008 or toss_decision != bat and city =
	 * bangalore
	 * 
	 * The query mentioned above in the example should return a List of Strings
	 * containing [or,and]
	 */
	public List<String> getLogicalOperatorsLogic(String qString){
		List<String> operators = new ArrayList<>();

		if(qString.contains(" where ")){
			String[] whereSplitArr = qString.split("where ");
			String[] spaceSplitArr = whereSplitArr[1].split(" ");
			for(String word : spaceSplitArr){
				if(word.trim().equals(("and")) || word.trim().equals("or")){
					operators.add(word);
				}
			}
			return operators;
		}

		return null;
	}

	/*
	 * Extract the aggregate functions from the query. The presence of the aggregate
	 * functions can determined if we have either "min" or "max" or "sum" or "count"
	 * or "avg" followed by opening braces"(" after "select" clause in the query
	 * string. in case it is present, then we will have to extract the same. For
	 * each aggregate functions, we need to know the following: 1. type of aggregate
	 * function(min/max/count/sum/avg) 2. field on which the aggregate function is
	 * being applied.
	 * 
	 * Please note that more than one aggregate function can be present in a query.
	 * 
	 * 
	 */
	public List<AggregateFunction> getAggregateFunctionsLogic(String qString){
		List<AggregateFunction> aggregateFunctionsList = new ArrayList<>();
		//String[] aggregateWords = {"min", "max", "sum", "count", "avg"};
		String[] aggregateWords = {"count", "avg", "min", "max", "sum"};

		//"select max(city),winner from ipl.csv where season > 2014 and city ='Bangalore' or city ='Delhi' group by winner"
		//{ "" | max(city),winner from ipl.csv where season > 2014 and city ='Bangalore' or city ='Delhi' group by winner}
		//{max(city),winner | ipl.csv where season > 2014 and city ='Bangalore' or city ='Delhi' group by winner}

		String[] selectArr = qString.split("select ");
		String[] fromArr = selectArr[1].split(" from ");
		//System.out.println("************ " + fromArr[0]);
		for(String aggWord : aggregateWords){
			if(fromArr[0].contains(aggWord)){
				String[] array = fromArr[0].trim().split(aggWord);
				//{"" | (city),winner} OR {"" | (city)}
				if(!array[1].contains(",")){
					aggregateFunctionsList.add(new AggregateFunction(array[1].substring(array[1].indexOf("(")+ 1,array[1].indexOf(")")), aggWord));
				} else {
					for(String splitItem: array){
						if(splitItem.contains("(")){
							if(splitItem.indexOf("(") -1 == -1) {
								aggregateFunctionsList.add(new AggregateFunction(splitItem.substring(splitItem.indexOf("(") + 1, splitItem.indexOf(")")), aggWord));
							}
						}
					}

				}

				System.out.println("From Array split at: " + aggWord);
				for(String arr: array){
					System.out.print( arr + " | ");
				}
				System.out.println("");

			}
		}
		return aggregateFunctionsList;
	}

}