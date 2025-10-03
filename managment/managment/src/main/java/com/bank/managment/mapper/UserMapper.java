    package com.bank.managment.mapper;

    import com.bank.managment.dto.request.CreateUserDTO;
    import com.bank.managment.dto.request.UpdateUserDTO;
    import com.bank.managment.dto.response.UserDTO;
    import com.bank.managment.entity.User;
    import org.mapstruct.Mapper;
    import org.mapstruct.Mapping;
    import org.mapstruct.MappingTarget;

    @Mapper(componentModel = "spring")
    public interface UserMapper {

        @Mapping(target = "idUser", ignore = true)
        @Mapping(target = "accounts", ignore = true)
        User toEntity(CreateUserDTO dto);

        UserDTO toDTO(User user);

        void updateEntity(@MappingTarget User user, UpdateUserDTO dto);
    }
