// const useStartWsConnection = () => {
//     const wsClient = useContext<Client>(WsContext);
//     const connect = wsClient.connect({}, _ => {
//         console.log("WS Client connected.");
//     });
//
//     return {connect};
// }
//
// const useStopWsConnection = () => {
//     const wsClient = useContext<Client>(WsContext);
//     const disconnect = wsClient.disconnect(() => console.log("WS Client disconnected."));
//     return {disconnect};
// }
//
//
// export {useStartWsConnection, useStopWsConnection};