package org.example.web.config;


import org.example.common.constants.SysConstants;
import org.example.web.redis.ShiroRedisTemplateDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@Component
@Slf4j
public class SessionRedisDao extends EnterpriseCacheSessionDAO {


    @Value("${shiro.session.timeout}")
    private   long expire ;

    @Autowired
    private ShiroRedisTemplateDao redisDao;

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = super.doCreate(session);
        long timeout = expire / 1000;
        if(session.getAttribute(SysConstants.SESSIONKEY)!=null) {
            redisDao.set(SysConstants.REDIS_SESSION_PRE +sessionId.toString(),
                    session,  timeout);
        }
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
    	   log.info("doReadSession:"+sessionId.toString());
        Session session = super.doReadSession(sessionId);
        if(session == null){

            session = (Session) redisDao.get(SysConstants.REDIS_SESSION_PRE
                    +sessionId.toString());
            log.info("本地时间不够，走了redis了"+session);
        }
        return session;
    }

    @Override
    protected void doUpdate(Session session) {
    	try {
            super.doUpdate(session);
    		 if(session.getAttribute(SysConstants.SESSIONKEY)!=null  ) {
                 Session  redisSession = (Session) redisDao.get(SysConstants.REDIS_SESSION_PRE
                         +session.getId());
                 if(redisSession==null || redisSession.getAttribute(SysConstants.SESSIONKEY)==null) {
                     long timeout = expire / 1000;
                     log.info("doUpdate:redis" );
                     redisDao.set(SysConstants.REDIS_SESSION_PRE
                                     +session.getId().toString(),
                             session,  timeout);
                 }
             }
    	}catch(Exception e) {
    		log.error("Session doUpdate "+e.getMessage());
    	}
    }

    
    // 删除session
    @Override
    public void delete(Session session) {
        super.delete(session);
        redisDao.delete(SysConstants.REDIS_SESSION_PRE +session.getId().toString());
          log.info("doDelete:"+session.getId().toString());
    }
    
  @Override
  public Collection<Session> getActiveSessions() {
      Set<Session> sessions = new HashSet<Session>();
      log.info("getActiveSessions");
      Set keys =  redisDao.getSetByPattern(SysConstants.REDIS_SESSION_PRE  );
      if (keys != null && keys.size() > 0) {
          for (Object key : keys) {
              sessions.add((Session) redisDao.get(key.toString()));
          }
      }
      return sessions;
  }
    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }


}
