package org.yunho.pharmacyrecommendation.util;

import com.opencsv.CSVReader;
import lombok.extern.slf4j.Slf4j;
import org.yunho.pharmacyrecommendation.pharmacy.dto.PharmacyDto;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class CsvUtils {

    public static List<PharmacyDto> convertToPharmacyDtoList() {

        String file = "./pharmacy2.csv";
        List<List<String>> csvList = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String[] values = null;
            while ((values = csvReader.readNext()) != null) {
                csvList.add(Arrays.asList(values));
            }
        } catch (IOException e) {
            log.error("CsvUtils convertToPharmacyDtoList Fail: {}", e.getMessage());
        }

        return IntStream.range(1, csvList.size()).mapToObj(index -> {
            List<String> rowList = csvList.get(index);

            String[] split = rowList.get(1).split(",");
            String pharmacyName = rowList.get(0);
            String pharmacyAddress = split[0];

            // pharmacyName과 pharmacyAddress 로그로 출력
            log.info("Pharmacy Name: {}", pharmacyName);
            log.info("Pharmacy Address: {}", pharmacyAddress);

            return PharmacyDto.builder()
                    .pharmacyName(pharmacyName)
                    .pharmacyAddress(pharmacyAddress)
                    .latitude(Double.parseDouble(rowList.get(4)))
                    .longitude(Double.parseDouble(rowList.get(5)))
                    .build();
        }).collect(Collectors.toList());
    }
}
