package hanium.dongguk.drug.drug.service;

import hanium.dongguk.drug.drug.domain.Drug;
import hanium.dongguk.drug.drug.domain.DrugRepository;
import hanium.dongguk.drug.drug.exception.DrugErrorCode;
import hanium.dongguk.global.exception.CommonException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DrugRetriever {
    private final DrugRepository drugRepository;

    public Drug findById(final UUID id) {
        return drugRepository.findById(id)
                .orElseThrow(() -> CommonException.type(DrugErrorCode.NOT_FOUND_DRUG));
    }
}
