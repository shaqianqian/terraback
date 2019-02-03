import asyncio
import websockets

async def receive_data(websocket, path):
    data = await websocket.recv()
    print("{data}")

start_server = websockets.serve(receive_data, 'localhost', 2681)
asyncio.get_event_loop().run_until_complete(receive_data)
asyncio.get_event_loop().run_forever()