package org.envirocar.app.injection.module;

import android.content.Context;

import org.envirocar.app.application.ContextInternetAccessProvider;
import org.envirocar.app.model.dao.AnnouncementsDAO;
import org.envirocar.app.model.dao.CacheDirectoryProvider;
import org.envirocar.app.model.dao.DAOProvider;
import org.envirocar.app.model.dao.FuelingDAO;
import org.envirocar.app.model.dao.InternetAccessProvider;
import org.envirocar.app.model.dao.SensorDAO;
import org.envirocar.app.model.dao.TermsOfUseDAO;
import org.envirocar.app.model.dao.TrackDAO;
import org.envirocar.app.model.dao.UserDAO;
import org.envirocar.app.model.dao.UserStatisticsDAO;
import org.envirocar.app.model.dao.cache.CacheAnnouncementsDAO;
import org.envirocar.app.model.dao.cache.CacheFuelingDAO;
import org.envirocar.app.model.dao.cache.CacheSensorDAO;
import org.envirocar.app.model.dao.cache.CacheTermsOfUseDAO;
import org.envirocar.app.model.dao.cache.CacheTrackDAO;
import org.envirocar.app.model.dao.cache.CacheUserDAO;
import org.envirocar.app.model.dao.remote.RemoteAnnouncementsDAO;
import org.envirocar.app.model.dao.remote.RemoteFuelingDAO;
import org.envirocar.app.model.dao.remote.RemoteSensorDAO;
import org.envirocar.app.model.dao.remote.RemoteTermsOfUseDAO;
import org.envirocar.app.model.dao.remote.RemoteTrackDAO;
import org.envirocar.app.model.dao.remote.RemoteUserDAO;
import org.envirocar.app.model.dao.remote.RemoteUserStatisticsDAO;
import org.envirocar.app.util.Util;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * A module for dao-specific dependencies which require a Application-
 * {@link android.content.Context} or to create. This includes specific Map- and
 * Bus-dependencies and
 *
 * @author dewall
 *
 */
@Module(
        injects = {
                DAOProvider.class,
                AnnouncementsDAO.class,
                RemoteAnnouncementsDAO.class,
                CacheAnnouncementsDAO.class,
                FuelingDAO.class,
                RemoteFuelingDAO.class,
                CacheFuelingDAO.class,
                SensorDAO.class,
                RemoteSensorDAO.class,
                CacheSensorDAO.class,
                TermsOfUseDAO.class,
                RemoteTermsOfUseDAO.class,
                CacheTermsOfUseDAO.class,
                TrackDAO.class,
                RemoteTrackDAO.class,
                CacheTrackDAO.class,
                UserDAO.class,
                RemoteUserDAO.class,
                CacheUserDAO.class,
                UserStatisticsDAO.class,
                RemoteUserStatisticsDAO.class
        },
        addsTo = InjectionApplicationModule.class,
        library = true,
        complete = false
)
public class InjectionDAOModule {

    private final Context mContext;

    /**
     * Constructor.
     *
     * @param context   the context of the current scope.
     */
    public InjectionDAOModule(Context context){
        this.mContext = context;
    }

    /**
     * Provides the InternetAccessProivder.
     *
     * @return the provider for internet access.
     */
    @Provides
    @Singleton
    public InternetAccessProvider provideInternetAccessProvider(){
        return new ContextInternetAccessProvider(mContext);
    }

    /**
     * Provides the CacheDirectoryProvider.
     *
     * @return the provider for cache access.
     */
    @Provides
    @Singleton
    public CacheDirectoryProvider provideCacheDirectoryProvider(){
        return new CacheDirectoryProvider() {
            @Override
            public File getBaseFolder() {
                return Util.resolveCacheFolder(mContext);
            }
        };
    }
}
