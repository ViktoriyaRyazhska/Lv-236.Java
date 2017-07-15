package com.inva.hipstertest.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.inva.hipstertest.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.inva.hipstertest.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.inva.hipstertest.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.inva.hipstertest.domain.School.class.getName(), jcacheConfiguration);
            cm.createCache(com.inva.hipstertest.domain.School.class.getName() + ".classrooms", jcacheConfiguration);
            cm.createCache(com.inva.hipstertest.domain.School.class.getName() + ".forms", jcacheConfiguration);
            cm.createCache(com.inva.hipstertest.domain.School.class.getName() + ".teachers", jcacheConfiguration);
            cm.createCache(com.inva.hipstertest.domain.Form.class.getName(), jcacheConfiguration);
            cm.createCache(com.inva.hipstertest.domain.Form.class.getName() + ".pupils", jcacheConfiguration);
            cm.createCache(com.inva.hipstertest.domain.Form.class.getName() + ".schedules", jcacheConfiguration);
            cm.createCache(com.inva.hipstertest.domain.Pupil.class.getName(), jcacheConfiguration);
            cm.createCache(com.inva.hipstertest.domain.Pupil.class.getName() + ".attendances", jcacheConfiguration);
            cm.createCache(com.inva.hipstertest.domain.Pupil.class.getName() + ".parents", jcacheConfiguration);
            cm.createCache(com.inva.hipstertest.domain.Teacher.class.getName(), jcacheConfiguration);
            cm.createCache(com.inva.hipstertest.domain.Teacher.class.getName() + ".lessons", jcacheConfiguration);
            cm.createCache(com.inva.hipstertest.domain.Teacher.class.getName() + ".schedules", jcacheConfiguration);
            cm.createCache(com.inva.hipstertest.domain.Classroom.class.getName(), jcacheConfiguration);
            cm.createCache(com.inva.hipstertest.domain.Classroom.class.getName() + ".schedules", jcacheConfiguration);
            cm.createCache(com.inva.hipstertest.domain.Lesson.class.getName(), jcacheConfiguration);
            cm.createCache(com.inva.hipstertest.domain.Lesson.class.getName() + ".schedules", jcacheConfiguration);
            cm.createCache(com.inva.hipstertest.domain.Lesson.class.getName() + ".teachers", jcacheConfiguration);
            cm.createCache(com.inva.hipstertest.domain.Schedule.class.getName(), jcacheConfiguration);
            cm.createCache(com.inva.hipstertest.domain.Schedule.class.getName() + ".attendances", jcacheConfiguration);
            cm.createCache(com.inva.hipstertest.domain.Attendances.class.getName(), jcacheConfiguration);
            cm.createCache(com.inva.hipstertest.domain.Parent.class.getName(), jcacheConfiguration);
            cm.createCache(com.inva.hipstertest.domain.Parent.class.getName() + ".pupils", jcacheConfiguration);
            cm.createCache(com.inva.hipstertest.domain.UserAddon.class.getName(), jcacheConfiguration);
            cm.createCache(com.inva.hipstertest.domain.LessonType.class.getName(), jcacheConfiguration);
            cm.createCache(com.inva.hipstertest.domain.AttendancesLog.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
