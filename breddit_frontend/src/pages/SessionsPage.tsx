import {bredditApi, useGetActiveSessionsQuery} from "../redux/query/breddit-api.ts";
import {useEffect} from "react";
import Button from "../components/Button.tsx";
import {Queries} from "../utils/constants.ts";
import {useDispatch, useSelector} from "react-redux";
import exportFromJSON from "export-from-json";
import {AuthState, selectAuthState} from "../redux/slices/authSlice.ts";


export default function SessionsPage() {
   const {data: sessions} = useGetActiveSessionsQuery();
   const authState: AuthState = useSelector(selectAuthState);
   const dispatch = useDispatch();

   useEffect(() => {
      console.log(sessions);
   }, [sessions]);

   const exportXML = () => {
      if (sessions) {
         exportFromJSON({
            data: sessions,
            fileName: "sessions",
            fields: [],
            exportType: "xml",
         });
      }
   }


   return (<div className="content-container bg-background-base text-background-text flex flex-col items-center justify-center">
         <div className="px-3 py-2 border border-common-gray rounded-xl">
            <table>
               <thead className='text-left bg-primary'>
               <tr className="border-b border-common-gray">
                  <th className="px-3 py-2">SessionID</th>
                  <th>Username</th>
               </tr>
               </thead>

               <tbody>

               {
                  sessions?.map(session => (
                     <tr key={session.sessionId} className="hover:bg-background-hover cursor-pointer">
                        <td className="py-2 px-3">{session.sessionId}</td>
                        <td>
                           <span>{session.user.username}</span>
                           {session.sessionId === authState.sessionId ?
                              <span className="ml-2 bg-green-400 py-1 px-2 rounded-xl font-bold">current</span> : <></>
                           }
                        </td>
                     </tr>
                  ))
               }

               </tbody>

            </table>
         </div>

         <div className="flex flex-row items-center justify-center gap-5 mt-5">
            <Button label="Invalidate cache" onClick={() => dispatch(bredditApi.util?.invalidateTags([Queries.getSessions]))}/>
            <Button label="Export to XML" onClick={exportXML}/>
         </div>

      </div>
   );
}