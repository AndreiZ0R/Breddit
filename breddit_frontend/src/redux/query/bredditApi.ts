import {createApi, fetchBaseQuery} from '@reduxjs/toolkit/query/react'

// export const bredditApi = createApi({
//     reducerPath: "bredditApi",
//     baseQuery: fetchBaseQuery({baseUrl: "http://localhost:8080/api"}),
//     endpoints: (builder) => ({
//         getUserById: builder.query<string, bigint>({
//             query: (id) => `users/${id}`
//         })
//     })
// });

export const pokemonApi = createApi({
    reducerPath: 'pokemonApi',
    baseQuery: fetchBaseQuery({baseUrl: 'https://pokeapi.co/api/v2/'}),
    endpoints: (builder) => ({
        getPokemonByName: builder.query<number, string>({
            query: (name) => `pokemon/${name}`,
        }),
    }),
})

export const {useGetPokemonByNameQuery} = pokemonApi;