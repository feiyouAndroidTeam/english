package com.yc.english.base.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.yc.english.group.model.bean.ClassInfo;
import com.yc.english.group.model.bean.StudentInfo;

import com.yc.english.base.dao.ClassInfoDao;
import com.yc.english.base.dao.StudentInfoDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig classInfoDaoConfig;
    private final DaoConfig studentInfoDaoConfig;

    private final ClassInfoDao classInfoDao;
    private final StudentInfoDao studentInfoDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        classInfoDaoConfig = daoConfigMap.get(ClassInfoDao.class).clone();
        classInfoDaoConfig.initIdentityScope(type);

        studentInfoDaoConfig = daoConfigMap.get(StudentInfoDao.class).clone();
        studentInfoDaoConfig.initIdentityScope(type);

        classInfoDao = new ClassInfoDao(classInfoDaoConfig, this);
        studentInfoDao = new StudentInfoDao(studentInfoDaoConfig, this);

        registerDao(ClassInfo.class, classInfoDao);
        registerDao(StudentInfo.class, studentInfoDao);
    }
    
    public void clear() {
        classInfoDaoConfig.clearIdentityScope();
        studentInfoDaoConfig.clearIdentityScope();
    }

    public ClassInfoDao getClassInfoDao() {
        return classInfoDao;
    }

    public StudentInfoDao getStudentInfoDao() {
        return studentInfoDao;
    }

}