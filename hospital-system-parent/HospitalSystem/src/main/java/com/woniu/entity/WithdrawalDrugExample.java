package com.woniu.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WithdrawalDrugExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public WithdrawalDrugExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andDrugOutIdIsNull() {
            addCriterion("drug_out_id is null");
            return (Criteria) this;
        }

        public Criteria andDrugOutIdIsNotNull() {
            addCriterion("drug_out_id is not null");
            return (Criteria) this;
        }

        public Criteria andDrugOutIdEqualTo(Integer value) {
            addCriterion("drug_out_id =", value, "drugOutId");
            return (Criteria) this;
        }

        public Criteria andDrugOutIdNotEqualTo(Integer value) {
            addCriterion("drug_out_id <>", value, "drugOutId");
            return (Criteria) this;
        }

        public Criteria andDrugOutIdGreaterThan(Integer value) {
            addCriterion("drug_out_id >", value, "drugOutId");
            return (Criteria) this;
        }

        public Criteria andDrugOutIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("drug_out_id >=", value, "drugOutId");
            return (Criteria) this;
        }

        public Criteria andDrugOutIdLessThan(Integer value) {
            addCriterion("drug_out_id <", value, "drugOutId");
            return (Criteria) this;
        }

        public Criteria andDrugOutIdLessThanOrEqualTo(Integer value) {
            addCriterion("drug_out_id <=", value, "drugOutId");
            return (Criteria) this;
        }

        public Criteria andDrugOutIdIn(List<Integer> values) {
            addCriterion("drug_out_id in", values, "drugOutId");
            return (Criteria) this;
        }

        public Criteria andDrugOutIdNotIn(List<Integer> values) {
            addCriterion("drug_out_id not in", values, "drugOutId");
            return (Criteria) this;
        }

        public Criteria andDrugOutIdBetween(Integer value1, Integer value2) {
            addCriterion("drug_out_id between", value1, value2, "drugOutId");
            return (Criteria) this;
        }

        public Criteria andDrugOutIdNotBetween(Integer value1, Integer value2) {
            addCriterion("drug_out_id not between", value1, value2, "drugOutId");
            return (Criteria) this;
        }

        public Criteria andOutDrugTimeIsNull() {
            addCriterion("out_drug_time is null");
            return (Criteria) this;
        }

        public Criteria andOutDrugTimeIsNotNull() {
            addCriterion("out_drug_time is not null");
            return (Criteria) this;
        }

        public Criteria andOutDrugTimeEqualTo(Date value) {
            addCriterion("out_drug_time =", value, "outDrugTime");
            return (Criteria) this;
        }

        public Criteria andOutDrugTimeNotEqualTo(Date value) {
            addCriterion("out_drug_time <>", value, "outDrugTime");
            return (Criteria) this;
        }

        public Criteria andOutDrugTimeGreaterThan(Date value) {
            addCriterion("out_drug_time >", value, "outDrugTime");
            return (Criteria) this;
        }

        public Criteria andOutDrugTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("out_drug_time >=", value, "outDrugTime");
            return (Criteria) this;
        }

        public Criteria andOutDrugTimeLessThan(Date value) {
            addCriterion("out_drug_time <", value, "outDrugTime");
            return (Criteria) this;
        }

        public Criteria andOutDrugTimeLessThanOrEqualTo(Date value) {
            addCriterion("out_drug_time <=", value, "outDrugTime");
            return (Criteria) this;
        }

        public Criteria andOutDrugTimeIn(List<Date> values) {
            addCriterion("out_drug_time in", values, "outDrugTime");
            return (Criteria) this;
        }

        public Criteria andOutDrugTimeNotIn(List<Date> values) {
            addCriterion("out_drug_time not in", values, "outDrugTime");
            return (Criteria) this;
        }

        public Criteria andOutDrugTimeBetween(Date value1, Date value2) {
            addCriterion("out_drug_time between", value1, value2, "outDrugTime");
            return (Criteria) this;
        }

        public Criteria andOutDrugTimeNotBetween(Date value1, Date value2) {
            addCriterion("out_drug_time not between", value1, value2, "outDrugTime");
            return (Criteria) this;
        }

        public Criteria andOutNameIsNull() {
            addCriterion("out_name is null");
            return (Criteria) this;
        }

        public Criteria andOutNameIsNotNull() {
            addCriterion("out_name is not null");
            return (Criteria) this;
        }

        public Criteria andOutNameEqualTo(String value) {
            addCriterion("out_name =", value, "outName");
            return (Criteria) this;
        }

        public Criteria andOutNameNotEqualTo(String value) {
            addCriterion("out_name <>", value, "outName");
            return (Criteria) this;
        }

        public Criteria andOutNameGreaterThan(String value) {
            addCriterion("out_name >", value, "outName");
            return (Criteria) this;
        }

        public Criteria andOutNameGreaterThanOrEqualTo(String value) {
            addCriterion("out_name >=", value, "outName");
            return (Criteria) this;
        }

        public Criteria andOutNameLessThan(String value) {
            addCriterion("out_name <", value, "outName");
            return (Criteria) this;
        }

        public Criteria andOutNameLessThanOrEqualTo(String value) {
            addCriterion("out_name <=", value, "outName");
            return (Criteria) this;
        }

        public Criteria andOutNameLike(String value) {
            addCriterion("out_name like", value, "outName");
            return (Criteria) this;
        }

        public Criteria andOutNameNotLike(String value) {
            addCriterion("out_name not like", value, "outName");
            return (Criteria) this;
        }

        public Criteria andOutNameIn(List<String> values) {
            addCriterion("out_name in", values, "outName");
            return (Criteria) this;
        }

        public Criteria andOutNameNotIn(List<String> values) {
            addCriterion("out_name not in", values, "outName");
            return (Criteria) this;
        }

        public Criteria andOutNameBetween(String value1, String value2) {
            addCriterion("out_name between", value1, value2, "outName");
            return (Criteria) this;
        }

        public Criteria andOutNameNotBetween(String value1, String value2) {
            addCriterion("out_name not between", value1, value2, "outName");
            return (Criteria) this;
        }

        public Criteria andSpare1IsNull() {
            addCriterion("spare1 is null");
            return (Criteria) this;
        }

        public Criteria andSpare1IsNotNull() {
            addCriterion("spare1 is not null");
            return (Criteria) this;
        }

        public Criteria andSpare1EqualTo(String value) {
            addCriterion("spare1 =", value, "spare1");
            return (Criteria) this;
        }

        public Criteria andSpare1NotEqualTo(String value) {
            addCriterion("spare1 <>", value, "spare1");
            return (Criteria) this;
        }

        public Criteria andSpare1GreaterThan(String value) {
            addCriterion("spare1 >", value, "spare1");
            return (Criteria) this;
        }

        public Criteria andSpare1GreaterThanOrEqualTo(String value) {
            addCriterion("spare1 >=", value, "spare1");
            return (Criteria) this;
        }

        public Criteria andSpare1LessThan(String value) {
            addCriterion("spare1 <", value, "spare1");
            return (Criteria) this;
        }

        public Criteria andSpare1LessThanOrEqualTo(String value) {
            addCriterion("spare1 <=", value, "spare1");
            return (Criteria) this;
        }

        public Criteria andSpare1Like(String value) {
            addCriterion("spare1 like", value, "spare1");
            return (Criteria) this;
        }

        public Criteria andSpare1NotLike(String value) {
            addCriterion("spare1 not like", value, "spare1");
            return (Criteria) this;
        }

        public Criteria andSpare1In(List<String> values) {
            addCriterion("spare1 in", values, "spare1");
            return (Criteria) this;
        }

        public Criteria andSpare1NotIn(List<String> values) {
            addCriterion("spare1 not in", values, "spare1");
            return (Criteria) this;
        }

        public Criteria andSpare1Between(String value1, String value2) {
            addCriterion("spare1 between", value1, value2, "spare1");
            return (Criteria) this;
        }

        public Criteria andSpare1NotBetween(String value1, String value2) {
            addCriterion("spare1 not between", value1, value2, "spare1");
            return (Criteria) this;
        }

        public Criteria andSpare2IsNull() {
            addCriterion("spare2 is null");
            return (Criteria) this;
        }

        public Criteria andSpare2IsNotNull() {
            addCriterion("spare2 is not null");
            return (Criteria) this;
        }

        public Criteria andSpare2EqualTo(String value) {
            addCriterion("spare2 =", value, "spare2");
            return (Criteria) this;
        }

        public Criteria andSpare2NotEqualTo(String value) {
            addCriterion("spare2 <>", value, "spare2");
            return (Criteria) this;
        }

        public Criteria andSpare2GreaterThan(String value) {
            addCriterion("spare2 >", value, "spare2");
            return (Criteria) this;
        }

        public Criteria andSpare2GreaterThanOrEqualTo(String value) {
            addCriterion("spare2 >=", value, "spare2");
            return (Criteria) this;
        }

        public Criteria andSpare2LessThan(String value) {
            addCriterion("spare2 <", value, "spare2");
            return (Criteria) this;
        }

        public Criteria andSpare2LessThanOrEqualTo(String value) {
            addCriterion("spare2 <=", value, "spare2");
            return (Criteria) this;
        }

        public Criteria andSpare2Like(String value) {
            addCriterion("spare2 like", value, "spare2");
            return (Criteria) this;
        }

        public Criteria andSpare2NotLike(String value) {
            addCriterion("spare2 not like", value, "spare2");
            return (Criteria) this;
        }

        public Criteria andSpare2In(List<String> values) {
            addCriterion("spare2 in", values, "spare2");
            return (Criteria) this;
        }

        public Criteria andSpare2NotIn(List<String> values) {
            addCriterion("spare2 not in", values, "spare2");
            return (Criteria) this;
        }

        public Criteria andSpare2Between(String value1, String value2) {
            addCriterion("spare2 between", value1, value2, "spare2");
            return (Criteria) this;
        }

        public Criteria andSpare2NotBetween(String value1, String value2) {
            addCriterion("spare2 not between", value1, value2, "spare2");
            return (Criteria) this;
        }

        public Criteria andSpare3IsNull() {
            addCriterion("spare3 is null");
            return (Criteria) this;
        }

        public Criteria andSpare3IsNotNull() {
            addCriterion("spare3 is not null");
            return (Criteria) this;
        }

        public Criteria andSpare3EqualTo(String value) {
            addCriterion("spare3 =", value, "spare3");
            return (Criteria) this;
        }

        public Criteria andSpare3NotEqualTo(String value) {
            addCriterion("spare3 <>", value, "spare3");
            return (Criteria) this;
        }

        public Criteria andSpare3GreaterThan(String value) {
            addCriterion("spare3 >", value, "spare3");
            return (Criteria) this;
        }

        public Criteria andSpare3GreaterThanOrEqualTo(String value) {
            addCriterion("spare3 >=", value, "spare3");
            return (Criteria) this;
        }

        public Criteria andSpare3LessThan(String value) {
            addCriterion("spare3 <", value, "spare3");
            return (Criteria) this;
        }

        public Criteria andSpare3LessThanOrEqualTo(String value) {
            addCriterion("spare3 <=", value, "spare3");
            return (Criteria) this;
        }

        public Criteria andSpare3Like(String value) {
            addCriterion("spare3 like", value, "spare3");
            return (Criteria) this;
        }

        public Criteria andSpare3NotLike(String value) {
            addCriterion("spare3 not like", value, "spare3");
            return (Criteria) this;
        }

        public Criteria andSpare3In(List<String> values) {
            addCriterion("spare3 in", values, "spare3");
            return (Criteria) this;
        }

        public Criteria andSpare3NotIn(List<String> values) {
            addCriterion("spare3 not in", values, "spare3");
            return (Criteria) this;
        }

        public Criteria andSpare3Between(String value1, String value2) {
            addCriterion("spare3 between", value1, value2, "spare3");
            return (Criteria) this;
        }

        public Criteria andSpare3NotBetween(String value1, String value2) {
            addCriterion("spare3 not between", value1, value2, "spare3");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}