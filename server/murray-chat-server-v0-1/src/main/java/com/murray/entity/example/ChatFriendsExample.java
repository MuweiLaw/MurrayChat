package com.murray.entity.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatFriendsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ChatFriendsExample() {
        oredCriteria = new ArrayList<>();
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
            criteria = new ArrayList<>();
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

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andChatUserNoIsNull() {
            addCriterion("chat_user_no is null");
            return (Criteria) this;
        }

        public Criteria andChatUserNoIsNotNull() {
            addCriterion("chat_user_no is not null");
            return (Criteria) this;
        }

        public Criteria andChatUserNoEqualTo(String value) {
            addCriterion("chat_user_no =", value, "chatUserNo");
            return (Criteria) this;
        }

        public Criteria andChatUserNoNotEqualTo(String value) {
            addCriterion("chat_user_no <>", value, "chatUserNo");
            return (Criteria) this;
        }

        public Criteria andChatUserNoGreaterThan(String value) {
            addCriterion("chat_user_no >", value, "chatUserNo");
            return (Criteria) this;
        }

        public Criteria andChatUserNoGreaterThanOrEqualTo(String value) {
            addCriterion("chat_user_no >=", value, "chatUserNo");
            return (Criteria) this;
        }

        public Criteria andChatUserNoLessThan(String value) {
            addCriterion("chat_user_no <", value, "chatUserNo");
            return (Criteria) this;
        }

        public Criteria andChatUserNoLessThanOrEqualTo(String value) {
            addCriterion("chat_user_no <=", value, "chatUserNo");
            return (Criteria) this;
        }

        public Criteria andChatUserNoLike(String value) {
            addCriterion("chat_user_no like", value, "chatUserNo");
            return (Criteria) this;
        }

        public Criteria andChatUserNoNotLike(String value) {
            addCriterion("chat_user_no not like", value, "chatUserNo");
            return (Criteria) this;
        }

        public Criteria andChatUserNoIn(List<String> values) {
            addCriterion("chat_user_no in", values, "chatUserNo");
            return (Criteria) this;
        }

        public Criteria andChatUserNoNotIn(List<String> values) {
            addCriterion("chat_user_no not in", values, "chatUserNo");
            return (Criteria) this;
        }

        public Criteria andChatUserNoBetween(String value1, String value2) {
            addCriterion("chat_user_no between", value1, value2, "chatUserNo");
            return (Criteria) this;
        }

        public Criteria andChatUserNoNotBetween(String value1, String value2) {
            addCriterion("chat_user_no not between", value1, value2, "chatUserNo");
            return (Criteria) this;
        }

        public Criteria andFriendUserNoIsNull() {
            addCriterion("friend_user_no is null");
            return (Criteria) this;
        }

        public Criteria andFriendUserNoIsNotNull() {
            addCriterion("friend_user_no is not null");
            return (Criteria) this;
        }

        public Criteria andFriendUserNoEqualTo(String value) {
            addCriterion("friend_user_no =", value, "friendUserNo");
            return (Criteria) this;
        }

        public Criteria andFriendUserNoNotEqualTo(String value) {
            addCriterion("friend_user_no <>", value, "friendUserNo");
            return (Criteria) this;
        }

        public Criteria andFriendUserNoGreaterThan(String value) {
            addCriterion("friend_user_no >", value, "friendUserNo");
            return (Criteria) this;
        }

        public Criteria andFriendUserNoGreaterThanOrEqualTo(String value) {
            addCriterion("friend_user_no >=", value, "friendUserNo");
            return (Criteria) this;
        }

        public Criteria andFriendUserNoLessThan(String value) {
            addCriterion("friend_user_no <", value, "friendUserNo");
            return (Criteria) this;
        }

        public Criteria andFriendUserNoLessThanOrEqualTo(String value) {
            addCriterion("friend_user_no <=", value, "friendUserNo");
            return (Criteria) this;
        }

        public Criteria andFriendUserNoLike(String value) {
            addCriterion("friend_user_no like", value, "friendUserNo");
            return (Criteria) this;
        }

        public Criteria andFriendUserNoNotLike(String value) {
            addCriterion("friend_user_no not like", value, "friendUserNo");
            return (Criteria) this;
        }

        public Criteria andFriendUserNoIn(List<String> values) {
            addCriterion("friend_user_no in", values, "friendUserNo");
            return (Criteria) this;
        }

        public Criteria andFriendUserNoNotIn(List<String> values) {
            addCriterion("friend_user_no not in", values, "friendUserNo");
            return (Criteria) this;
        }

        public Criteria andFriendUserNoBetween(String value1, String value2) {
            addCriterion("friend_user_no between", value1, value2, "friendUserNo");
            return (Criteria) this;
        }

        public Criteria andFriendUserNoNotBetween(String value1, String value2) {
            addCriterion("friend_user_no not between", value1, value2, "friendUserNo");
            return (Criteria) this;
        }

        public Criteria andNoteNameIsNull() {
            addCriterion("note_name is null");
            return (Criteria) this;
        }

        public Criteria andNoteNameIsNotNull() {
            addCriterion("note_name is not null");
            return (Criteria) this;
        }

        public Criteria andNoteNameEqualTo(String value) {
            addCriterion("note_name =", value, "noteName");
            return (Criteria) this;
        }

        public Criteria andNoteNameNotEqualTo(String value) {
            addCriterion("note_name <>", value, "noteName");
            return (Criteria) this;
        }

        public Criteria andNoteNameGreaterThan(String value) {
            addCriterion("note_name >", value, "noteName");
            return (Criteria) this;
        }

        public Criteria andNoteNameGreaterThanOrEqualTo(String value) {
            addCriterion("note_name >=", value, "noteName");
            return (Criteria) this;
        }

        public Criteria andNoteNameLessThan(String value) {
            addCriterion("note_name <", value, "noteName");
            return (Criteria) this;
        }

        public Criteria andNoteNameLessThanOrEqualTo(String value) {
            addCriterion("note_name <=", value, "noteName");
            return (Criteria) this;
        }

        public Criteria andNoteNameLike(String value) {
            addCriterion("note_name like", value, "noteName");
            return (Criteria) this;
        }

        public Criteria andNoteNameNotLike(String value) {
            addCriterion("note_name not like", value, "noteName");
            return (Criteria) this;
        }

        public Criteria andNoteNameIn(List<String> values) {
            addCriterion("note_name in", values, "noteName");
            return (Criteria) this;
        }

        public Criteria andNoteNameNotIn(List<String> values) {
            addCriterion("note_name not in", values, "noteName");
            return (Criteria) this;
        }

        public Criteria andNoteNameBetween(String value1, String value2) {
            addCriterion("note_name between", value1, value2, "noteName");
            return (Criteria) this;
        }

        public Criteria andNoteNameNotBetween(String value1, String value2) {
            addCriterion("note_name not between", value1, value2, "noteName");
            return (Criteria) this;
        }

        public Criteria andSeeMeIsNull() {
            addCriterion("see_me is null");
            return (Criteria) this;
        }

        public Criteria andSeeMeIsNotNull() {
            addCriterion("see_me is not null");
            return (Criteria) this;
        }

        public Criteria andSeeMeEqualTo(Boolean value) {
            addCriterion("see_me =", value, "seeMe");
            return (Criteria) this;
        }

        public Criteria andSeeMeNotEqualTo(Boolean value) {
            addCriterion("see_me <>", value, "seeMe");
            return (Criteria) this;
        }

        public Criteria andSeeMeGreaterThan(Boolean value) {
            addCriterion("see_me >", value, "seeMe");
            return (Criteria) this;
        }

        public Criteria andSeeMeGreaterThanOrEqualTo(Boolean value) {
            addCriterion("see_me >=", value, "seeMe");
            return (Criteria) this;
        }

        public Criteria andSeeMeLessThan(Boolean value) {
            addCriterion("see_me <", value, "seeMe");
            return (Criteria) this;
        }

        public Criteria andSeeMeLessThanOrEqualTo(Boolean value) {
            addCriterion("see_me <=", value, "seeMe");
            return (Criteria) this;
        }

        public Criteria andSeeMeIn(List<Boolean> values) {
            addCriterion("see_me in", values, "seeMe");
            return (Criteria) this;
        }

        public Criteria andSeeMeNotIn(List<Boolean> values) {
            addCriterion("see_me not in", values, "seeMe");
            return (Criteria) this;
        }

        public Criteria andSeeMeBetween(Boolean value1, Boolean value2) {
            addCriterion("see_me between", value1, value2, "seeMe");
            return (Criteria) this;
        }

        public Criteria andSeeMeNotBetween(Boolean value1, Boolean value2) {
            addCriterion("see_me not between", value1, value2, "seeMe");
            return (Criteria) this;
        }

        public Criteria andSeeOtherPartyIsNull() {
            addCriterion("see_other_party is null");
            return (Criteria) this;
        }

        public Criteria andSeeOtherPartyIsNotNull() {
            addCriterion("see_other_party is not null");
            return (Criteria) this;
        }

        public Criteria andSeeOtherPartyEqualTo(Boolean value) {
            addCriterion("see_other_party =", value, "seeOtherParty");
            return (Criteria) this;
        }

        public Criteria andSeeOtherPartyNotEqualTo(Boolean value) {
            addCriterion("see_other_party <>", value, "seeOtherParty");
            return (Criteria) this;
        }

        public Criteria andSeeOtherPartyGreaterThan(Boolean value) {
            addCriterion("see_other_party >", value, "seeOtherParty");
            return (Criteria) this;
        }

        public Criteria andSeeOtherPartyGreaterThanOrEqualTo(Boolean value) {
            addCriterion("see_other_party >=", value, "seeOtherParty");
            return (Criteria) this;
        }

        public Criteria andSeeOtherPartyLessThan(Boolean value) {
            addCriterion("see_other_party <", value, "seeOtherParty");
            return (Criteria) this;
        }

        public Criteria andSeeOtherPartyLessThanOrEqualTo(Boolean value) {
            addCriterion("see_other_party <=", value, "seeOtherParty");
            return (Criteria) this;
        }

        public Criteria andSeeOtherPartyIn(List<Boolean> values) {
            addCriterion("see_other_party in", values, "seeOtherParty");
            return (Criteria) this;
        }

        public Criteria andSeeOtherPartyNotIn(List<Boolean> values) {
            addCriterion("see_other_party not in", values, "seeOtherParty");
            return (Criteria) this;
        }

        public Criteria andSeeOtherPartyBetween(Boolean value1, Boolean value2) {
            addCriterion("see_other_party between", value1, value2, "seeOtherParty");
            return (Criteria) this;
        }

        public Criteria andSeeOtherPartyNotBetween(Boolean value1, Boolean value2) {
            addCriterion("see_other_party not between", value1, value2, "seeOtherParty");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }
    }

    /**
     */
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