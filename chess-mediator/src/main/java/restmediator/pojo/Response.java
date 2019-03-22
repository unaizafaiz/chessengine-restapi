package restmediator.pojo;

/**
 * POJO to receive response
 * status = indicates pass, fail or other
 * message
 * nextmove: can be null if no next move to be made or player is white and first move must be obtained from user
 */
public class Response {
    int status;
    NextMove nextmove = new NextMove();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public NextMove getNextMove() {
        return nextmove;
    }

    public void setNextMove(NextMove nextMove) {
        this.nextmove = nextMove;
    }

    @Override
    public String toString() {
        return "Response{" +
                "status=" + status +
                ", nextmove=" + nextmove +
                ", message='" + message + '\'' +
                '}';
    }
}
