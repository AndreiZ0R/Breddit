// import {createListenerMiddleware} from "@reduxjs/toolkit";
// import {closeConnection, startConnection} from "../slices/websocketSlice.ts";
// import SockJS from "sockjs-client"
// import {Message, over} from "stompjs";
//
// // const websocketMiddleware: Middleware<{}, RootState> = storeApi => next => action => {
// //
// //     let socket = null;
// //     let client = null;
// //
// //     const {connected} = storeApi.getState().websocket;
// //
// //     // if (!connected) {
// //     //     storeApi.dispatch(startConnection());
// //     //     console.log("start");
// //     //     return next(action);
// //     // }
// //     // return null;
// //     return next(action);
// //
// //
// //     // if (!connected) {
// //     //     // socket = new SockJS('http://localhost:8080/socket');
// //     //     // client = over(socket);
// //     //     //
// //     //     // client.connect({}, _ => {
// //     //     //     console.log("WS client connected");
// //     //     // })
// //     //     console.log("Doing connection!")
// //     //     store.dispatch(startConnection());
// //     // }
// //
// //
// // }
//
// const connectionWsMiddleware = createListenerMiddleware();
// const sock: WebSocket = new SockJS('http://localhost:8080/socket');
// const stompClient = over(sock);
//
//
// connectionWsMiddleware.startListening({
//     actionCreator: startConnection,
//     effect: (action, api) => {
//         stompClient.connect({}, _ => {
//             stompClient.subscribe("/topic/notifications", (msg: Message) => {
//                 console.log(msg);
//             })
//         })
//     }
// });
//
//
// const closeConnectionWsMiddleware = createListenerMiddleware();
// closeConnectionWsMiddleware.startListening({
//     actionCreator: closeConnection,
//     effect: (action, api) => {
//         sock.close();
//         stompClient.disconnect(() => {
//             console.log("Disconnected WS");
//         })
//     }
// })
//
// export {connectionWsMiddleware, closeConnectionWsMiddleware};