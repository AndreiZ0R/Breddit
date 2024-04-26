import {bredditApi, useGetActiveSessionsQuery} from "../redux/query/breddit-api.ts";
import {useEffect} from "react";
import Button from "../components/Button.tsx";
import {Queries} from "../utils/constants.ts";
import {useDispatch} from "react-redux";

export default function SessionsPage() {
    const {data: sessions} = useGetActiveSessionsQuery();
    const dispatch = useDispatch();

    useEffect(() => {
        console.log(sessions);
    }, [sessions]);

    return (<>
        <table>
            <thead className='text-center bg-primary'>
            <tr>
                <th>SessionID</th>
                <th>Username</th>
            </tr>
            </thead>

            <tbody>

            {
                sessions?.map(session => (
                    <tr key={session.sessionId}>
                        <td>{session.sessionId}</td>
                        <td>{session.user.username}</td>
                    </tr>
                ))
            }

            </tbody>

        </table>

        <Button label="Invalidate cache" onClick={() => dispatch(bredditApi.util?.invalidateTags([Queries.getSessions]))}/>
    </>);
}