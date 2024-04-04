import {useDispatch, useSelector} from "react-redux";
import {decrement, increment, selectCount} from "./redux/slices/counterSlice.ts";
import {useGetPokemonByNameQuery} from "./redux/query/bredditApi.ts";
import {useEffect} from "react";

function App() {
    const count = useSelector(selectCount);
    const dispatch = useDispatch();

    const {data} = useGetPokemonByNameQuery("bulbasaur");

    useEffect(() => {
        console.log(data);
    }, [data])

    return (
        <>
            <div>
                <button onClick={() => dispatch(increment())}>Increment</button>
                <span>{count}</span>
                <button onClick={() => dispatch(decrement())}>Increment</button>
            </div>
        </>
    )
}

export default App
