import { useCallback, useEffect, useState } from 'react';
import { useChatWebSocket } from '../../hooks/useChatWebSocket';

interface Message {
  from: String,
  content: String
};

export const App = () => {

  const [connected, setConnected] = useState(false);
  const { $connected, $inputMessage, $outputMessage } = useChatWebSocket<Message>();

  useEffect(() => {
    $connected.subscribe(setConnected);
    $inputMessage.subscribe(message => {
      console.log(`You received a message from ${message.from}: ${message.content}`);
    });
  }, []);

  const sendMessage = useCallback(() => {
    console.log('Sending message.');
    const message = {
      from: 'Frontend client',
      content: 'Hello, world!'
    } as Message;
    $outputMessage.next(message);
  }, [$outputMessage]);

  return (
    <div>
      <p>Connected? {String(connected)}</p>
      <button
        disabled={!connected}
        onClick={sendMessage}>Send message</button>
    </div >
  );
};
