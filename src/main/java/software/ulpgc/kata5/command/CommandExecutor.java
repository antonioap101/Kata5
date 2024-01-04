package software.ulpgc.kata5.command;

import spark.Request;
import spark.Response;

public record CommandExecutor(Request request, Response response) {

    public String execute(Command command){
        Command.Output output = command.execute(input());
        response.status(output.code());
        return output.result();
    }

    private Command.Input input() {
        return new Command.Input() {
            @Override
            public String get(String key) {
                return oneOf(request.params(key), request.queryParams(key));
            }

            private String oneOf(String params, String queryParams) {
                return params != null ? params : queryParams;
            }
        };
    }
}
