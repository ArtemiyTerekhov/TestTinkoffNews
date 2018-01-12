package terekhov.artemiy.testtinkoffnews.data.db;

import com.snappydb.DB;
import com.snappydb.SnappydbException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import terekhov.artemiy.testtinkoffnews.data.db.exception.CacheExpiredException;
import terekhov.artemiy.testtinkoffnews.data.db.exception.KeyIsNullException;
import terekhov.artemiy.testtinkoffnews.data.db.exception.MissingDataException;
import terekhov.artemiy.testtinkoffnews.data.db.exception.RxSnappyException;
import terekhov.artemiy.testtinkoffnews.data.db.exception.ValueIsNullException;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public class BaseSnappyClient {
    protected final DB mDatabase;

    public BaseSnappyClient(DB db) {
        mDatabase = db;
        if (mDatabase == null) {
            throw new RuntimeException("Database is null!");
        }
    }

    private boolean checkIsCacheValid(String key, Long cacheTime) throws RxSnappyException {
        if (cacheTime == null) {
            return true;
        } else {
            String[] splitKey = key.split(":");
            long diff = System.currentTimeMillis() - Long.valueOf(splitKey[1]);

            if (diff <= cacheTime) {
                return true;
            } else {
                throw new CacheExpiredException();
            }
        }
    }

    private String generateKey(String key) {
        return key + ":" + System.currentTimeMillis();
    }

    protected Boolean removeCacheForKey(String key) throws SnappydbException, RxSnappyException {
        synchronized (mDatabase) {
            if (mDatabase.countKeys(key) > 0) {
                String[] keys = fndKeys(key);
                for (String s : keys) {
                    mDatabase.del(s);
                }
            }

            return true;
        }
    }

    private void removePreviousCachedElement(String key) throws SnappydbException {
        synchronized (mDatabase) {
            if (mDatabase.countKeys(key) > 1) {
                String[] s = mDatabase.findKeys(key);
                mDatabase.del(s[0]);
            }
        }
    }

    protected String[] fndKeys(String key) throws SnappydbException, RxSnappyException {
        synchronized (mDatabase) {
            String[] s = mDatabase.findKeys(key);
            if (s.length == 0) {
                throw new MissingDataException();
            }
            return s;
        }
    }

    protected Integer cntKeys(String key) throws SnappydbException {
        synchronized (mDatabase) {
            return mDatabase.countKeys(key);
        }
    }

    private String findTimeBasedKey(String key) throws SnappydbException {
        synchronized (mDatabase) {
            String[] s = mDatabase.findKeys(key, 0);
            if (s == null || s.length == 0) {
                return null;
            }
            return s[0];
        }
    }

    public boolean isInCache(String key) throws SnappydbException {
        synchronized (mDatabase) {
            String[] keys = mDatabase.findKeys(key, 0);
            return (keys.length > 0);
        }
    }

    public boolean existsKey(String key) throws SnappydbException {
        synchronized (mDatabase) {
            return mDatabase.exists(key);
        }
    }

    protected boolean isInCache(String key, Long cacheTime) throws SnappydbException, RxSnappyException {
        synchronized (mDatabase) {
            String[] keys = mDatabase.findKeys(key);
            return (keys.length > 0) && (checkIsCacheValid(keys[0], cacheTime));
        }
    }

    protected Boolean setValue(String key, Boolean value, boolean ignoreCache) throws SnappydbException, RxSnappyException {
        synchronized (mDatabase) {
            if (key == null) {
                throw new KeyIsNullException();
            }

            if (value == null) {
                throw new ValueIsNullException();
            }

            if (!ignoreCache) {
                mDatabase.put(generateKey(key), value);
                removePreviousCachedElement(key);
            } else {
                mDatabase.put(key, value);
            }

            return value;
        }
    }

    protected Boolean getBooleanValue(String key, Long cacheTime) throws SnappydbException, RxSnappyException {
        synchronized (mDatabase) {
            if (key == null) {
                throw new KeyIsNullException();
            }
            if (isInCache(key, cacheTime)) {
                return mDatabase.getBoolean(findTimeBasedKey(key));
            } else {
                throw new MissingDataException();
            }
        }
    }

    protected Integer setValue(String key, Integer value, boolean ignoreCache) throws SnappydbException, RxSnappyException {
        synchronized (mDatabase) {
            if (key == null) {
                throw new KeyIsNullException();
            }
            if (value == null) {
                throw new ValueIsNullException();
            }
            if (!ignoreCache) {
                mDatabase.put(generateKey(key), value);
                removePreviousCachedElement(key);
            } else {
                mDatabase.put(key, value);
            }

            return value;
        }
    }

    protected Integer getIntegerValue(String key, Long cacheTime) throws SnappydbException, RxSnappyException {
        synchronized (mDatabase) {
            if (key == null) {
                throw new KeyIsNullException();
            }
            if (isInCache(key, cacheTime)) {
                return mDatabase.getObject(findTimeBasedKey(key), Integer.class);
            } else {
                throw new MissingDataException();
            }
        }
    }

    protected Long setValue(String key, Long value, boolean ignoreCache) throws SnappydbException, RxSnappyException {
        synchronized (mDatabase) {
            if (key == null) {
                throw new KeyIsNullException();
            }
            if (value == null) {
                throw new ValueIsNullException();
            }
            if (!ignoreCache) {
                mDatabase.put(generateKey(key), value);
                removePreviousCachedElement(key);
            } else {
                mDatabase.put(key, value);
            }

            return value;
        }
    }

    protected Long getLongValue(String key, Long cacheTime) throws SnappydbException, RxSnappyException {
        synchronized (mDatabase) {
            if (key == null) {
                throw new KeyIsNullException();
            }
            if (isInCache(key, cacheTime)) {
                return mDatabase.getObject(findTimeBasedKey(key), Long.class);
            } else {
                throw new MissingDataException();
            }
        }
    }

    protected String setValue(String key, String value, boolean ignoreCache) throws SnappydbException, RxSnappyException {
        synchronized (mDatabase) {
            if (key == null) {
                throw new KeyIsNullException();
            }
            if (value == null) {
                throw new ValueIsNullException();
            }
            if (!ignoreCache) {
                mDatabase.put(generateKey(key), value);
                removePreviousCachedElement(key);
            } else {
                mDatabase.put(key, value);
            }

            return value;
        }
    }

    protected String getStringValue(String key, Long cacheTime) throws SnappydbException, RxSnappyException {
        synchronized (mDatabase) {
            if (key == null) {
                throw new KeyIsNullException();
            }
            if (isInCache(key, cacheTime)) {
                return mDatabase.get(findTimeBasedKey(key));
            } else {
                throw new MissingDataException();
            }
        }
    }

    protected List<String> setStringListValue(String key, List<String> value, boolean ignoreCache) throws SnappydbException, RxSnappyException {
        synchronized (mDatabase) {
            if (key == null) {
                throw new KeyIsNullException();
            }
            if (value == null) {
                throw new ValueIsNullException();
            }

            if (!ignoreCache) {
                mDatabase.put(generateKey(key), value.toArray(new String[value.size()]));
                removePreviousCachedElement(key);
            } else {
                mDatabase.put(key, value.toArray(new String[value.size()]));
            }

            return value;
        }
    }


    protected List<String> getStringListValue(String key, Long cacheTime) throws SnappydbException, RxSnappyException {
        synchronized (mDatabase) {
            if (key == null) {
                throw new KeyIsNullException();
            }
            List<String> values = new ArrayList<String>();
            if (isInCache(key, cacheTime)) {
                values.addAll(Arrays.asList(mDatabase.getArray(findTimeBasedKey(key), String.class)));
            } else {
                throw new MissingDataException();
            }
            return values;
        }
    }


    protected <T> List<T> getObjectList(String key, Long cacheTime, Class<T> resultClass) throws SnappydbException, RxSnappyException {
        synchronized (mDatabase) {
            if (key == null) {
                throw new KeyIsNullException();
            }
            List<T> resultList = new ArrayList<T>();
            if (isInCache(key, cacheTime)) {
                resultList.addAll(Arrays.asList(mDatabase.getObjectArray(findTimeBasedKey(key), resultClass)));
            } else {
                throw new MissingDataException();
            }
            return resultList;
        }
    }

    protected <T> List<T> setValue(String key, List<T> object, boolean ignoreCache) throws SnappydbException, RxSnappyException {
        synchronized (mDatabase) {
            if (key == null) {
                throw new KeyIsNullException();
            }
            if (object == null) {
                throw new ValueIsNullException();
            }

            if (!ignoreCache) {
                mDatabase.put(generateKey(key), object.toArray());
                removePreviousCachedElement(key);
            } else {
                mDatabase.put(key, object.toArray());
            }

            return object;
        }
    }

    protected <T> T setValue(String key, T o, boolean ignoreCache) throws SnappydbException, RxSnappyException {
        synchronized (mDatabase) {
            if (key == null) {
                throw new KeyIsNullException();
            }
            if (o == null) {
                throw new ValueIsNullException();
            }

            if (!ignoreCache) {
                mDatabase.put(generateKey(key), o);
                removePreviousCachedElement(key);
            } else {
                mDatabase.put(key, o);
            }

            return o;
        }
    }

    protected <T> T getObjectValue(String key, Long cacheTime, Class<T> resultClass) throws SnappydbException, RxSnappyException {
        synchronized (mDatabase) {
            if (key == null) {
                throw new KeyIsNullException();
            }
            if (isInCache(key, cacheTime)) {
                return mDatabase.getObject(findTimeBasedKey(key), resultClass);
            } else {
                throw new MissingDataException();
            }
        }
    }
}
