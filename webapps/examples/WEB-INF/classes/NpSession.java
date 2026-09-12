import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class NpSession {

    private static final Log log = LogFactory.getLog(NpSession.class);

    private static final Map<String, NpSession> sessions = new HashMap<>();

    private final String id;
    private final OutputStream outputStream;

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public String getId() {
        return id;
    }

    public NpSession(String id, OutputStream outputStream) {
        this.id = id;
        this.outputStream = outputStream;
    }

    public void bind() {}

    public synchronized static void createSession(String id, OutputStream outputStream) {
        NpSession session = new NpSession(id, outputStream);
        sessions.put(id, session);
    }

    public synchronized static NpSession bindSession(String id) {
        NpSession session = sessions.get(id);
        if (session != null) {
            session.bind();
        }
        return session;
    }

}
