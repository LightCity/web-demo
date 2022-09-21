package me.longli.demo.configuration;

import org.jobrunr.configuration.JobRunr;
import org.jobrunr.scheduling.JobRequestScheduler;
import org.jobrunr.scheduling.JobScheduler;
import org.jobrunr.storage.StorageProvider;
import org.jobrunr.storage.StorageProviderUtils;
import org.jobrunr.storage.sql.common.SqlStorageProviderFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;
import org.jobrunr.configuration.JobRunrConfiguration.JobRunrConfigurationResult;

@Configuration
//@Import(JobRunrStorageConfiguration.class)
public class JobRunrConfiguration {

    @Bean
    public JobRunrConfigurationResult initJobRunr(ApplicationContext applicationContext) {
        DataSource dataSource = applicationContext.getBean(DataSource.class);
        StorageProvider storageProvider = SqlStorageProviderFactory.using(dataSource, null, StorageProviderUtils.DatabaseOptions.SKIP_CREATE);

        return JobRunr.configure()
                .useJobActivator(applicationContext::getBean)
                .useStorageProvider(storageProvider)
                .useBackgroundJobServer()
                .useJmxExtensions()
                .useDashboard(39999)
                .initialize();
    }

    @Bean
    public JobScheduler initJobScheduler(JobRunrConfigurationResult jobRunrConfigurationResult) {
        return jobRunrConfigurationResult.getJobScheduler();
    }

    @Bean
    public JobRequestScheduler initJobRequestScheduler(JobRunrConfigurationResult jobRunrConfigurationResult) {
        return jobRunrConfigurationResult.getJobRequestScheduler();
    }
}
