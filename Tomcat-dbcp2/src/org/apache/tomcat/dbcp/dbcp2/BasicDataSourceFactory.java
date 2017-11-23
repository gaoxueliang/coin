/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.apache.tomcat.dbcp.dbcp2;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
/**
 *
 * @author git
 */
public class BasicDataSourceFactory 

  implements ObjectFactory
{
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
  private static final Log log = LogFactory.getLog(BasicDataSourceFactory.class);
  
  private static final String PROP_DEFAULTAUTOCOMMIT = "defaultAutoCommit";
  
  private static final String PROP_DEFAULTREADONLY = "defaultReadOnly";
  
  private static final String PROP_DEFAULTTRANSACTIONISOLATION = "defaultTransactionIsolation";
  
  private static final String PROP_DEFAULTCATALOG = "defaultCatalog";
  
  private static final String PROP_CACHESTATE = "cacheState";
  
  private static final String PROP_DRIVERCLASSNAME = "driverClassName";
  
  private static final String PROP_LIFO = "lifo";
  
  private static final String PROP_MAXTOTAL = "maxTotal";
  
  private static final String PROP_MAXIDLE = "maxIdle";
  
  private static final String PROP_MINIDLE = "minIdle";
  
  private static final String PROP_INITIALSIZE = "initialSize";
  
  private static final String PROP_MAXWAITMILLIS = "maxWaitMillis";
  
  private static final String PROP_TESTONCREATE = "testOnCreate";
  
  private static final String PROP_TESTONBORROW = "testOnBorrow";
  
  private static final String PROP_TESTONRETURN = "testOnReturn";
  
  private static final String PROP_TIMEBETWEENEVICTIONRUNSMILLIS = "timeBetweenEvictionRunsMillis";
  
  private static final String PROP_NUMTESTSPEREVICTIONRUN = "numTestsPerEvictionRun";
  
  private static final String PROP_MINEVICTABLEIDLETIMEMILLIS = "minEvictableIdleTimeMillis";
  
  private static final String PROP_SOFTMINEVICTABLEIDLETIMEMILLIS = "softMinEvictableIdleTimeMillis";
  
  private static final String PROP_EVICTIONPOLICYCLASSNAME = "evictionPolicyClassName";
  
  private static final String PROP_TESTWHILEIDLE = "testWhileIdle";
  
  private static final String PROP_PASSWORD = "password";
  private static final String PROP_URL = "url";
  private static final String PROP_USERNAME = "username";
  private static final String PROP_VALIDATIONQUERY = "validationQuery";
  private static final String PROP_VALIDATIONQUERY_TIMEOUT = "validationQueryTimeout";
  private static final String PROP_JMX_NAME = "jmxName";
  private static final String PROP_CONNECTIONINITSQLS = "connectionInitSqls";
  private static final String PROP_ACCESSTOUNDERLYINGCONNECTIONALLOWED = "accessToUnderlyingConnectionAllowed";
  private static final String PROP_REMOVEABANDONEDONBORROW = "removeAbandonedOnBorrow";
  private static final String PROP_REMOVEABANDONEDONMAINTENANCE = "removeAbandonedOnMaintenance";
  private static final String PROP_REMOVEABANDONEDTIMEOUT = "removeAbandonedTimeout";
  private static final String PROP_LOGABANDONED = "logAbandoned";
  private static final String PROP_ABANDONEDUSAGETRACKING = "abandonedUsageTracking";
  private static final String PROP_POOLPREPAREDSTATEMENTS = "poolPreparedStatements";
  private static final String PROP_MAXOPENPREPAREDSTATEMENTS = "maxOpenPreparedStatements";
  private static final String PROP_CONNECTIONPROPERTIES = "connectionProperties";
  private static final String PROP_MAXCONNLIFETIMEMILLIS = "maxConnLifetimeMillis";
  private static final String PROP_LOGEXPIREDCONNECTIONS = "logExpiredConnections";
  private static final String PROP_ROLLBACK_ON_RETURN = "rollbackOnReturn";
  private static final String PROP_ENABLE_AUTOCOMMIT_ON_RETURN = "enableAutoCommitOnReturn";
  private static final String PROP_DEFAULT_QUERYTIMEOUT = "defaultQueryTimeout";
  private static final String PROP_FASTFAIL_VALIDATION = "fastFailValidation";
  private static final String PROP_DISCONNECTION_SQL_CODES = "disconnectionSqlCodes";
  private static final String NUPROP_MAXACTIVE = "maxActive";
  private static final String NUPROP_REMOVEABANDONED = "removeAbandoned";
  private static final String NUPROP_MAXWAIT = "maxWait";
  private static final String SILENTPROP_FACTORY = "factory";
  private static final String SILENTPROP_SCOPE = "scope";
  private static final String SILENTPROP_SINGLETON = "singleton";
  private static final String SILENTPROP_AUTH = "auth";
  private static final String[] ALL_PROPERTIES = { "defaultAutoCommit", "defaultReadOnly", "defaultTransactionIsolation", "defaultCatalog", "cacheState", "driverClassName", "lifo", "maxTotal", "maxIdle", "minIdle", "initialSize", "maxWaitMillis", "testOnCreate", "testOnBorrow", "testOnReturn", "timeBetweenEvictionRunsMillis", "numTestsPerEvictionRun", "minEvictableIdleTimeMillis", "softMinEvictableIdleTimeMillis", "evictionPolicyClassName", "testWhileIdle", "password", "url", "username", "validationQuery", "validationQueryTimeout", "connectionInitSqls", "accessToUnderlyingConnectionAllowed", "removeAbandonedOnBorrow", "removeAbandonedOnMaintenance", "removeAbandonedTimeout", "logAbandoned", "abandonedUsageTracking", "poolPreparedStatements", "maxOpenPreparedStatements", "connectionProperties", "maxConnLifetimeMillis", "logExpiredConnections", "rollbackOnReturn", "enableAutoCommitOnReturn", "defaultQueryTimeout", "fastFailValidation", "disconnectionSqlCodes" };
  

















































  private static final Map<String, String> NUPROP_WARNTEXT = new LinkedHashMap();
  private static final List<String> SILENT_PROPERTIES;
  
  static { NUPROP_WARNTEXT.put("maxActive", "Property maxActive is not used in DBCP2, use maxTotal instead. maxTotal default value is 8.");
    


    NUPROP_WARNTEXT.put("removeAbandoned", "Property removeAbandoned is not used in DBCP2, use one or both of removeAbandonedOnBorrow or removeAbandonedOnMaintenance instead. Both have default value set to false.");
    




    NUPROP_WARNTEXT.put("maxWait", "Property maxWait is not used in DBCP2 , use maxWaitMillis instead. maxWaitMillis default value is -1.");
    










    SILENT_PROPERTIES = new ArrayList();
    

    SILENT_PROPERTIES.add("factory");
    SILENT_PROPERTIES.add("scope");
    SILENT_PROPERTIES.add("singleton");
    SILENT_PROPERTIES.add("auth");
  }
  





















  public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable<?, ?> environment)
    throws Exception
  {
    if ((obj == null) || (!(obj instanceof Reference))) {
      return null;
    }
    Reference ref = (Reference)obj;
    if (!"javax.sql.DataSource".equals(ref.getClassName())) {
      return null;
    }
    

    List<String> warnings = new ArrayList();
    List<String> infoMessages = new ArrayList();
    validatePropertyNames(ref, name, warnings, infoMessages);
    for (String warning : warnings) {
      log.warn(warning);
    }
    for (String infoMessage : infoMessages) {
      log.info(infoMessage);
    }
    
    Properties properties = new Properties();
    for (String propertyName : ALL_PROPERTIES) {
      RefAddr ra = ref.get(propertyName);
      if (ra != null) {
        String propertyValue = ra.getContent().toString();
        properties.setProperty(propertyName, propertyValue);
      }
    }
    
    return createDataSource(properties);
  }
  









  private void validatePropertyNames(Reference ref, Name name, List<String> warnings, List<String> infoMessages)
  {
    List<String> allPropsAsList = Arrays.asList(ALL_PROPERTIES);
    String nameString = name != null ? "Name = " + name.toString() + " " : "";
    if ((NUPROP_WARNTEXT != null) && (!NUPROP_WARNTEXT.keySet().isEmpty())) {
      for (String propertyName : NUPROP_WARNTEXT.keySet()) {
        RefAddr ra = ref.get(propertyName);
        if ((ra != null) && (!allPropsAsList.contains(ra.getType()))) {
          StringBuilder stringBuilder = new StringBuilder(nameString);
          String propertyValue = ra.getContent().toString();
          stringBuilder.append((String)NUPROP_WARNTEXT.get(propertyName)).append(" You have set value of \"").append(propertyValue).append("\" for \"").append(propertyName).append("\" property, which is being ignored.");
          




          warnings.add(stringBuilder.toString());
        }
      }
    }
    
    Enumeration<RefAddr> allRefAddrs = ref.getAll();
    while (allRefAddrs.hasMoreElements()) {
      RefAddr ra = (RefAddr)allRefAddrs.nextElement();
      String propertyName = ra.getType();
      

      if ((!allPropsAsList.contains(propertyName)) && (!NUPROP_WARNTEXT.keySet().contains(propertyName)) && (!SILENT_PROPERTIES.contains(propertyName)))
      {

        String propertyValue = ra.getContent().toString();
        StringBuilder stringBuilder = new StringBuilder(nameString);
        stringBuilder.append("Ignoring unknown property: ").append("value of \"").append(propertyValue).append("\" for \"").append(propertyName).append("\" property");
        




        infoMessages.add(stringBuilder.toString());
      }
    }
  }
  





  public static BasicDataSource createDataSource(Properties properties)
    throws Exception
  {
    BasicDataSource dataSource = new BasicDataSource();
    String value = null;
    
    value = properties.getProperty("defaultAutoCommit");
    if (value != null) {
      dataSource.setDefaultAutoCommit(Boolean.valueOf(value));
    }
    
    value = properties.getProperty("defaultReadOnly");
    if (value != null) {
      dataSource.setDefaultReadOnly(Boolean.valueOf(value));
    }
    
    value = properties.getProperty("defaultTransactionIsolation");
    if (value != null) {
      int level = -1;
      if ("NONE".equalsIgnoreCase(value)) {
        level = 0;
      }
      else if ("READ_COMMITTED".equalsIgnoreCase(value)) {
        level = 2;
      }
      else if ("READ_UNCOMMITTED".equalsIgnoreCase(value)) {
        level = 1;
      }
      else if ("REPEATABLE_READ".equalsIgnoreCase(value)) {
        level = 4;
      }
      else if ("SERIALIZABLE".equalsIgnoreCase(value)) {
        level = 8;
      } else {
        try
        {
          level = Integer.parseInt(value);
        } catch (NumberFormatException e) {
          System.err.println("Could not parse defaultTransactionIsolation: " + value);
          System.err.println("WARNING: defaultTransactionIsolation not set");
          System.err.println("using default value of database driver");
          level = -1;
        }
      }
      dataSource.setDefaultTransactionIsolation(level);
    }
    
    value = properties.getProperty("defaultCatalog");
    if (value != null) {
      dataSource.setDefaultCatalog(value);
    }
    
    value = properties.getProperty("cacheState");
    if (value != null) {
      dataSource.setCacheState(Boolean.valueOf(value).booleanValue());
    }
    
    value = properties.getProperty("driverClassName");
    if (value != null) {
      dataSource.setDriverClassName(value);
    }
    
    value = properties.getProperty("lifo");
    if (value != null) {
      dataSource.setLifo(Boolean.valueOf(value).booleanValue());
    }
    
    value = properties.getProperty("maxTotal");
    if (value != null) {
      dataSource.setMaxTotal(Integer.parseInt(value));
    }
    
    value = properties.getProperty("maxIdle");
    if (value != null) {
      dataSource.setMaxIdle(Integer.parseInt(value));
    }
    
    value = properties.getProperty("minIdle");
    if (value != null) {
      dataSource.setMinIdle(Integer.parseInt(value));
    }
    
    value = properties.getProperty("initialSize");
    if (value != null) {
      dataSource.setInitialSize(Integer.parseInt(value));
    }
    
    value = properties.getProperty("maxWaitMillis");
    if (value != null) {
      dataSource.setMaxWaitMillis(Long.parseLong(value));
    }
    
    value = properties.getProperty("testOnCreate");
    if (value != null) {
      dataSource.setTestOnCreate(Boolean.valueOf(value).booleanValue());
    }
    
    value = properties.getProperty("testOnBorrow");
    if (value != null) {
      dataSource.setTestOnBorrow(Boolean.valueOf(value).booleanValue());
    }
    
    value = properties.getProperty("testOnReturn");
    if (value != null) {
      dataSource.setTestOnReturn(Boolean.valueOf(value).booleanValue());
    }
    
    value = properties.getProperty("timeBetweenEvictionRunsMillis");
    if (value != null) {
      dataSource.setTimeBetweenEvictionRunsMillis(Long.parseLong(value));
    }
    
    value = properties.getProperty("numTestsPerEvictionRun");
    if (value != null) {
      dataSource.setNumTestsPerEvictionRun(Integer.parseInt(value));
    }
    
    value = properties.getProperty("minEvictableIdleTimeMillis");
    if (value != null) {
      dataSource.setMinEvictableIdleTimeMillis(Long.parseLong(value));
    }
    
    value = properties.getProperty("softMinEvictableIdleTimeMillis");
    if (value != null) {
      dataSource.setSoftMinEvictableIdleTimeMillis(Long.parseLong(value));
    }
    
    value = properties.getProperty("evictionPolicyClassName");
    if (value != null) {
      dataSource.setEvictionPolicyClassName(value);
    }
    
    value = properties.getProperty("testWhileIdle");
    if (value != null) {
      dataSource.setTestWhileIdle(Boolean.valueOf(value).booleanValue());
    }
    
    value = properties.getProperty("password");
    if (value != null) {
      dataSource.setPassword(value);
    }
    
    value = properties.getProperty("url");
    if (value != null) {
      dataSource.setUrl(value);
    }
    
    value = properties.getProperty("username");
    if (value != null) {
      dataSource.setUsername(value);
    }
    
    value = properties.getProperty("validationQuery");
    if (value != null) {
      dataSource.setValidationQuery(value);
    }
    
    value = properties.getProperty("validationQueryTimeout");
    if (value != null) {
      dataSource.setValidationQueryTimeout(Integer.parseInt(value));
    }
    
    value = properties.getProperty("accessToUnderlyingConnectionAllowed");
    if (value != null) {
      dataSource.setAccessToUnderlyingConnectionAllowed(Boolean.valueOf(value).booleanValue());
    }
    
    value = properties.getProperty("removeAbandonedOnBorrow");
    if (value != null) {
      dataSource.setRemoveAbandonedOnBorrow(Boolean.valueOf(value).booleanValue());
    }
    
    value = properties.getProperty("removeAbandonedOnMaintenance");
    if (value != null) {
      dataSource.setRemoveAbandonedOnMaintenance(Boolean.valueOf(value).booleanValue());
    }
    
    value = properties.getProperty("removeAbandonedTimeout");
    if (value != null) {
      dataSource.setRemoveAbandonedTimeout(Integer.parseInt(value));
    }
    
    value = properties.getProperty("logAbandoned");
    if (value != null) {
      dataSource.setLogAbandoned(Boolean.valueOf(value).booleanValue());
    }
    
    value = properties.getProperty("abandonedUsageTracking");
    if (value != null) {
      dataSource.setAbandonedUsageTracking(Boolean.valueOf(value).booleanValue());
    }
    
    value = properties.getProperty("poolPreparedStatements");
    if (value != null) {
      dataSource.setPoolPreparedStatements(Boolean.valueOf(value).booleanValue());
    }
    
    value = properties.getProperty("maxOpenPreparedStatements");
    if (value != null) {
      dataSource.setMaxOpenPreparedStatements(Integer.parseInt(value));
    }
    
    value = properties.getProperty("connectionInitSqls");
    if (value != null) {
      dataSource.setConnectionInitSqls(parseList(value, ';'));
    }
    
    value = properties.getProperty("connectionProperties");
    if (value != null) {
      Properties p = getProperties(value);
      Enumeration<?> e = p.propertyNames();
      while (e.hasMoreElements()) {
        String propertyName = (String)e.nextElement();
        dataSource.addConnectionProperty(propertyName, p.getProperty(propertyName));
      }
    }
    
    value = properties.getProperty("maxConnLifetimeMillis");
    if (value != null) {
      dataSource.setMaxConnLifetimeMillis(Long.parseLong(value));
    }
    
    value = properties.getProperty("logExpiredConnections");
    if (value != null) {
      dataSource.setLogExpiredConnections(Boolean.valueOf(value).booleanValue());
    }
    
    value = properties.getProperty("jmxName");
    if (value != null) {
      dataSource.setJmxName(value);
    }
    
    value = properties.getProperty("enableAutoCommitOnReturn");
    if (value != null) {
      dataSource.setEnableAutoCommitOnReturn(Boolean.valueOf(value).booleanValue());
    }
    
    value = properties.getProperty("rollbackOnReturn");
    if (value != null) {
      dataSource.setRollbackOnReturn(Boolean.valueOf(value).booleanValue());
    }
    
    value = properties.getProperty("defaultQueryTimeout");
    if (value != null) {
      dataSource.setDefaultQueryTimeout(Integer.valueOf(value));
    }
    
    value = properties.getProperty("fastFailValidation");
    if (value != null) {
      dataSource.setFastFailValidation(Boolean.valueOf(value).booleanValue());
    }
    
    value = properties.getProperty("disconnectionSqlCodes");
    if (value != null) {
      dataSource.setDisconnectionSqlCodes(parseList(value, ','));
    }
    


    if (dataSource.getInitialSize() > 0) {
      dataSource.getLogWriter();
    }
    

    return dataSource;
  }
  




  private static Properties getProperties(String propText)
    throws Exception
  {
    Properties p = new Properties();
    if (propText != null) {
      p.load(new ByteArrayInputStream(propText.replace(';', '\n').getBytes(StandardCharsets.ISO_8859_1)));
    }
    
    return p;
  }
  





  private static Collection<String> parseList(String value, char delimiter)
  {
    StringTokenizer tokenizer = new StringTokenizer(value, Character.toString(delimiter));
    Collection<String> tokens = new ArrayList(tokenizer.countTokens());
    while (tokenizer.hasMoreTokens()) {
      tokens.add(tokenizer.nextToken());
    }
    return tokens;
  }
}