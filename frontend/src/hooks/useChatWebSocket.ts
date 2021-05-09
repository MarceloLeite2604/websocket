import { useEffect, useState } from 'react';
import { Subject } from 'rxjs';
import { w3cwebsocket as W3CWebSocket } from 'websocket';

const SERVER_ADDRESS = 'ws://localhost:8080/chat';

function createClient<T>($connected : Subject<boolean>, $inputMessage : Subject<T>) {
  const client = new W3CWebSocket(SERVER_ADDRESS);
  client.onopen = () => {
    $connected.next(true);
  };

  client.onclose = () => {
    $connected.next(false);
  };

  client.onmessage = (message) => {
    if (message.data instanceof Buffer || message.data instanceof ArrayBuffer) {
      throw new Error('Unsuported message data type.');
    }
  
    const data = JSON.parse(message.data) as T;
    $inputMessage.next(data);
  };

  return client;
}

export function useChatWebSocket<T>() {
  const [$connected] = useState(new Subject<boolean>());
  const [$inputMessage] = useState(new Subject<T>());
  const [$outputMessage] = useState(new Subject<T>());
  const [client] = useState(createClient($connected, $inputMessage));

  useEffect(() => {
    $outputMessage.subscribe(data => {
      client.send(JSON.stringify(data));
    });
  }, []);

  return {
    $connected,
    $inputMessage,
    $outputMessage
  };
};
