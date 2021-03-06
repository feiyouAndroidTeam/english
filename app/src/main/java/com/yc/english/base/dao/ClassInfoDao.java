package com.yc.english.base.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.yc.english.group.model.bean.ClassInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CLASS_INFO".
*/
public class ClassInfoDao extends AbstractDao<ClassInfo, Long> {

    public static final String TABLENAME = "CLASS_INFO";

    /**
     * Properties of entity ClassInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property CId = new Property(0, Long.class, "cId", true, "_id");
        public final static Property ImageUrl = new Property(1, String.class, "imageUrl", false, "IMAGE_URL");
        public final static Property ClassName = new Property(2, String.class, "className", false, "CLASS_NAME");
        public final static Property Count = new Property(3, String.class, "count", false, "COUNT");
        public final static Property GroupId = new Property(4, int.class, "groupId", false, "GROUP_ID");
        public final static Property Founder_id = new Property(5, String.class, "founder_id", false, "FOUNDER_ID");
        public final static Property Master_id = new Property(6, String.class, "master_id", false, "MASTER_ID");
        public final static Property Vali_type = new Property(7, String.class, "vali_type", false, "VALI_TYPE");
        public final static Property Is_del = new Property(8, String.class, "is_del", false, "IS_DEL");
        public final static Property Add_time = new Property(9, String.class, "add_time", false, "ADD_TIME");
        public final static Property Add_date = new Property(10, String.class, "add_date", false, "ADD_DATE");
        public final static Property Del_time = new Property(11, String.class, "del_time", false, "DEL_TIME");
        public final static Property Sort = new Property(12, String.class, "sort", false, "SORT");
        public final static Property Master_name = new Property(13, String.class, "master_name", false, "MASTER_NAME");
        public final static Property Master_nick_name = new Property(14, String.class, "master_nick_name", false, "MASTER_NICK_NAME");
        public final static Property Class_id = new Property(15, String.class, "class_id", false, "CLASS_ID");
        public final static Property Flag = new Property(16, String.class, "flag", false, "FLAG");
        public final static Property Fee_type = new Property(17, int.class, "fee_type", false, "FEE_TYPE");
        public final static Property Type = new Property(18, String.class, "type", false, "TYPE");
        public final static Property Is_allow_talk = new Property(19, int.class, "is_allow_talk", false, "IS_ALLOW_TALK");
        public final static Property Desp_url = new Property(20, String.class, "desp_url", false, "DESP_URL");
        public final static Property Title = new Property(21, String.class, "title", false, "TITLE");
        public final static Property Role = new Property(22, int.class, "role", false, "ROLE");
    }


    public ClassInfoDao(DaoConfig config) {
        super(config);
    }
    
    public ClassInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CLASS_INFO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: cId
                "\"IMAGE_URL\" TEXT," + // 1: imageUrl
                "\"CLASS_NAME\" TEXT," + // 2: className
                "\"COUNT\" TEXT," + // 3: count
                "\"GROUP_ID\" INTEGER NOT NULL ," + // 4: groupId
                "\"FOUNDER_ID\" TEXT," + // 5: founder_id
                "\"MASTER_ID\" TEXT," + // 6: master_id
                "\"VALI_TYPE\" TEXT," + // 7: vali_type
                "\"IS_DEL\" TEXT," + // 8: is_del
                "\"ADD_TIME\" TEXT," + // 9: add_time
                "\"ADD_DATE\" TEXT," + // 10: add_date
                "\"DEL_TIME\" TEXT," + // 11: del_time
                "\"SORT\" TEXT," + // 12: sort
                "\"MASTER_NAME\" TEXT," + // 13: master_name
                "\"MASTER_NICK_NAME\" TEXT," + // 14: master_nick_name
                "\"CLASS_ID\" TEXT," + // 15: class_id
                "\"FLAG\" TEXT," + // 16: flag
                "\"FEE_TYPE\" INTEGER NOT NULL ," + // 17: fee_type
                "\"TYPE\" TEXT," + // 18: type
                "\"IS_ALLOW_TALK\" INTEGER NOT NULL ," + // 19: is_allow_talk
                "\"DESP_URL\" TEXT," + // 20: desp_url
                "\"TITLE\" TEXT," + // 21: title
                "\"ROLE\" INTEGER NOT NULL );"); // 22: role
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CLASS_INFO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ClassInfo entity) {
        stmt.clearBindings();
 
        Long cId = entity.getCId();
        if (cId != null) {
            stmt.bindLong(1, cId);
        }
 
        String imageUrl = entity.getImageUrl();
        if (imageUrl != null) {
            stmt.bindString(2, imageUrl);
        }
 
        String className = entity.getClassName();
        if (className != null) {
            stmt.bindString(3, className);
        }
 
        String count = entity.getCount();
        if (count != null) {
            stmt.bindString(4, count);
        }
        stmt.bindLong(5, entity.getGroupId());
 
        String founder_id = entity.getFounder_id();
        if (founder_id != null) {
            stmt.bindString(6, founder_id);
        }
 
        String master_id = entity.getMaster_id();
        if (master_id != null) {
            stmt.bindString(7, master_id);
        }
 
        String vali_type = entity.getVali_type();
        if (vali_type != null) {
            stmt.bindString(8, vali_type);
        }
 
        String is_del = entity.getIs_del();
        if (is_del != null) {
            stmt.bindString(9, is_del);
        }
 
        String add_time = entity.getAdd_time();
        if (add_time != null) {
            stmt.bindString(10, add_time);
        }
 
        String add_date = entity.getAdd_date();
        if (add_date != null) {
            stmt.bindString(11, add_date);
        }
 
        String del_time = entity.getDel_time();
        if (del_time != null) {
            stmt.bindString(12, del_time);
        }
 
        String sort = entity.getSort();
        if (sort != null) {
            stmt.bindString(13, sort);
        }
 
        String master_name = entity.getMaster_name();
        if (master_name != null) {
            stmt.bindString(14, master_name);
        }
 
        String master_nick_name = entity.getMaster_nick_name();
        if (master_nick_name != null) {
            stmt.bindString(15, master_nick_name);
        }
 
        String class_id = entity.getClass_id();
        if (class_id != null) {
            stmt.bindString(16, class_id);
        }
 
        String flag = entity.getFlag();
        if (flag != null) {
            stmt.bindString(17, flag);
        }
        stmt.bindLong(18, entity.getFee_type());
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(19, type);
        }
        stmt.bindLong(20, entity.getIs_allow_talk());
 
        String desp_url = entity.getDesp_url();
        if (desp_url != null) {
            stmt.bindString(21, desp_url);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(22, title);
        }
        stmt.bindLong(23, entity.getRole());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ClassInfo entity) {
        stmt.clearBindings();
 
        Long cId = entity.getCId();
        if (cId != null) {
            stmt.bindLong(1, cId);
        }
 
        String imageUrl = entity.getImageUrl();
        if (imageUrl != null) {
            stmt.bindString(2, imageUrl);
        }
 
        String className = entity.getClassName();
        if (className != null) {
            stmt.bindString(3, className);
        }
 
        String count = entity.getCount();
        if (count != null) {
            stmt.bindString(4, count);
        }
        stmt.bindLong(5, entity.getGroupId());
 
        String founder_id = entity.getFounder_id();
        if (founder_id != null) {
            stmt.bindString(6, founder_id);
        }
 
        String master_id = entity.getMaster_id();
        if (master_id != null) {
            stmt.bindString(7, master_id);
        }
 
        String vali_type = entity.getVali_type();
        if (vali_type != null) {
            stmt.bindString(8, vali_type);
        }
 
        String is_del = entity.getIs_del();
        if (is_del != null) {
            stmt.bindString(9, is_del);
        }
 
        String add_time = entity.getAdd_time();
        if (add_time != null) {
            stmt.bindString(10, add_time);
        }
 
        String add_date = entity.getAdd_date();
        if (add_date != null) {
            stmt.bindString(11, add_date);
        }
 
        String del_time = entity.getDel_time();
        if (del_time != null) {
            stmt.bindString(12, del_time);
        }
 
        String sort = entity.getSort();
        if (sort != null) {
            stmt.bindString(13, sort);
        }
 
        String master_name = entity.getMaster_name();
        if (master_name != null) {
            stmt.bindString(14, master_name);
        }
 
        String master_nick_name = entity.getMaster_nick_name();
        if (master_nick_name != null) {
            stmt.bindString(15, master_nick_name);
        }
 
        String class_id = entity.getClass_id();
        if (class_id != null) {
            stmt.bindString(16, class_id);
        }
 
        String flag = entity.getFlag();
        if (flag != null) {
            stmt.bindString(17, flag);
        }
        stmt.bindLong(18, entity.getFee_type());
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(19, type);
        }
        stmt.bindLong(20, entity.getIs_allow_talk());
 
        String desp_url = entity.getDesp_url();
        if (desp_url != null) {
            stmt.bindString(21, desp_url);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(22, title);
        }
        stmt.bindLong(23, entity.getRole());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public ClassInfo readEntity(Cursor cursor, int offset) {
        ClassInfo entity = new ClassInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // cId
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // imageUrl
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // className
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // count
            cursor.getInt(offset + 4), // groupId
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // founder_id
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // master_id
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // vali_type
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // is_del
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // add_time
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // add_date
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // del_time
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // sort
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // master_name
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // master_nick_name
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // class_id
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // flag
            cursor.getInt(offset + 17), // fee_type
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // type
            cursor.getInt(offset + 19), // is_allow_talk
            cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20), // desp_url
            cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21), // title
            cursor.getInt(offset + 22) // role
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ClassInfo entity, int offset) {
        entity.setCId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setImageUrl(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setClassName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setCount(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setGroupId(cursor.getInt(offset + 4));
        entity.setFounder_id(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setMaster_id(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setVali_type(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setIs_del(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setAdd_time(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setAdd_date(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setDel_time(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setSort(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setMaster_name(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setMaster_nick_name(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setClass_id(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setFlag(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setFee_type(cursor.getInt(offset + 17));
        entity.setType(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setIs_allow_talk(cursor.getInt(offset + 19));
        entity.setDesp_url(cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20));
        entity.setTitle(cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21));
        entity.setRole(cursor.getInt(offset + 22));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(ClassInfo entity, long rowId) {
        entity.setCId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(ClassInfo entity) {
        if(entity != null) {
            return entity.getCId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ClassInfo entity) {
        return entity.getCId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
