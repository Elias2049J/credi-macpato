package com.runaitec.credimacpato.service.impl;

import com.runaitec.credimacpato.dto.user.UserRequestDTO;
import com.runaitec.credimacpato.dto.user.UserResponseDTO;
import com.runaitec.credimacpato.entity.UserState;
import com.runaitec.credimacpato.entity.user.User;
import com.runaitec.credimacpato.mapper.UserMapper;
import com.runaitec.credimacpato.repository.UserRepository;
import com.runaitec.credimacpato.mapper.RestMapper;
import com.runaitec.credimacpato.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDTO create(UserRequestDTO request) {
        return userMapper.toResponseDtoDispatch(
                userRepository.save(userMapper.toEntityDispatch(request))
        );
    }

    @Override
    public List<UserResponseDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponseDtoDispatch)
                .toList();
    }

    @Override
    public UserResponseDTO findById(Long aLong) {
        return userMapper.toResponseDtoDispatch(getEntityById(aLong));
    }

    @Override
    public UserResponseDTO disable(Long id) {
        User user = getEntityById(id);
        user.setState(UserState.DISABLED);
        return userMapper.toResponseDtoDispatch(
                userRepository.save(user)
        );
    }

    @Override
    public UserResponseDTO enable(Long id) {
        User user = getEntityById(id);
        user.setState(UserState.ENABLED);
        return userMapper.toResponseDtoDispatch(
                userRepository.save(user)
        );
    }

    @Override
    public UserResponseDTO block(Long id) {
        User user = getEntityById(id);
        user.setState(UserState.BLOCKED);
        return userMapper.toResponseDtoDispatch(
                userRepository.save(user)
        );
    }

    @Override
    public JpaRepository<User, Long> repository() {
        return userRepository;
    }

    @Override
    public RestMapper<User, UserResponseDTO, UserRequestDTO> mapper() {
        return userMapper;
    }

    @Override
    public UserResponseDTO update(Long aLong, UserRequestDTO request) {
        if (!userRepository.existsById(aLong)){
            throw new EntityNotFoundException("User not found");
        }
        User userToUpdate = userMapper.toEntityDispatch(request);
        userToUpdate.setId(aLong);
        return userMapper.toResponseDtoDispatch(
                userRepository.save(userToUpdate)
        );
    }
}
