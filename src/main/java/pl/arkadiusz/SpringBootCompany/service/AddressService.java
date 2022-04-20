package pl.arkadiusz.SpringBootCompany.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.arkadiusz.SpringBootCompany.model.Address;
import pl.arkadiusz.SpringBootCompany.repository.AddressRepository;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;


    public Address findAddressById(Long id) {
        return addressRepository.findAddressById(id);
    }

}
