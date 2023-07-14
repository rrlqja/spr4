package song.spring4.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.spring4.entity.LogEntity;
import song.spring4.repository.LogEntityJpaRepository;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogEntityService {

    private final LogEntityJpaRepository logEntityRepository;

    @Transactional
    public Long save(String className, String methodName, LocalDateTime requestTime, Long resultTime) {

        LogEntity logEntity = new LogEntity();
        logEntity.setClassName(className);
        logEntity.setMethodName(methodName);
        logEntity.setRequestTime(requestTime);
        logEntity.setResultTimeMs(resultTime);

        LogEntity saveLogEntity = logEntityRepository.save(logEntity);

        return saveLogEntity.getId();
    }
}
