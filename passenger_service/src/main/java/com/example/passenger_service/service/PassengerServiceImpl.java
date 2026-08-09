package com.example.passenger_service.service;

import com.example.passenger_service.dto.PassengerRequestDto;
import com.example.passenger_service.dto.PassengerResponseDto;
import com.example.passenger_service.exception.PassengerIdNotFoundException;
import com.example.passenger_service.exception.handleArgumentException;
import com.example.passenger_service.model.Passenger;
import com.example.passenger_service.repository.PassengerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PassengerServiceImpl implements PassengerService {
    @Autowired
    PassengerRepository passengerRepository;
    @Autowired ModelMapper modelMapper;

    @Override
    @Transactional
    public PassengerResponseDto addPassenger(PassengerRequestDto PassengerRequestDto){
        if (passengerRepository.existsByIdentityNumber(PassengerRequestDto.getIdentityNumber())) {
                throw new handleArgumentException();
        }
        Passenger passenger = modelMapper.map(PassengerRequestDto,Passenger.class);
        Passenger passenger1 =  passengerRepository.save(passenger);
        return modelMapper.map(passenger1, PassengerResponseDto.class);
        }

        @Override
        @Transactional(readOnly = true)
        public List<PassengerResponseDto> getAllPassenger(){
         return passengerRepository.findAll().stream()
                .map(Passenger -> modelMapper.map(Passenger, PassengerResponseDto.class))
                .collect(Collectors.toList());

    }

    @Override
    @Transactional(readOnly = true)
    public PassengerResponseDto getPassengerById(Integer PassengerId){
     Passenger passenger = passengerRepository.findById(PassengerId)
             .orElseThrow(() -> new PassengerIdNotFoundException(PassengerId));
             return modelMapper.map(passenger, PassengerResponseDto.class);
    }

    @Override
    @Transactional
    public PassengerResponseDto updatePassenger(Integer PassengerId , PassengerRequestDto updatePassenger){
        Passenger oldData = passengerRepository.findById(PassengerId)
                .orElseThrow(() -> new PassengerIdNotFoundException(PassengerId));
        modelMapper.map(updatePassenger, oldData);
        Passenger Passenger = passengerRepository.save(oldData);
        return modelMapper.map(Passenger, PassengerResponseDto.class);
    }
    @Override
    @Transactional
    public void deletePassenger(Integer PassengerId){
        Passenger Passenger = passengerRepository.findById(PassengerId)
                .orElseThrow(() -> new PassengerIdNotFoundException(PassengerId));
        passengerRepository.delete(Passenger);
    }

}



