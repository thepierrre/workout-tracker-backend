//package com.example.gymapp.mappers;
//
//public interface Mapper<A, B> {
//
//    B mapTo(A a);
//
//    A mapFrom(B b);
//}

package com.example.gymapp.mappers;

public interface Mapper<Entity, Dto> {

    Dto mapToDto(Entity entity);

    Entity mapFromDto(Dto dto);
}
