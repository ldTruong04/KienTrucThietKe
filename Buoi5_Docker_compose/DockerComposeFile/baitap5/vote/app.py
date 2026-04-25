from flask import Flask, render_template_string, request, make_response
import redis
import os
import socket
import json

app = Flask(__name__)

redis_host = os.environ.get('REDIS_HOST', 'redis')
r = redis.Redis(host=redis_host, port=6379, db=0)

TEMPLATE = '''
<!DOCTYPE html>
<html>
<head>
    <title>Voting App</title>
    <style>
        body { font-family: Arial, sans-serif; background: #2d2d2d; color: #fff; display: flex; justify-content: center; align-items: center; min-height: 100vh; margin: 0; }
        .container { text-align: center; }
        h1 { font-size: 2.5rem; margin-bottom: 30px; }
        .btn { display: inline-block; padding: 20px 60px; margin: 10px; font-size: 1.2rem; border: none; border-radius: 10px; cursor: pointer; color: #fff; text-decoration: none; }
        .btn-a { background: #0074D9; }
        .btn-b { background: #FF4136; }
        .btn:hover { opacity: 0.85; }
        .info { margin-top: 20px; color: #aaa; font-size: 0.9rem; }
    </style>
</head>
<body>
    <div class="container">
        <h1>🗳️ Vote!</h1>
        <form method="POST">
            <button class="btn btn-a" name="vote" value="a" type="submit">Cats</button>
            <button class="btn btn-b" name="vote" value="b" type="submit">Dogs</button>
        </form>
        <p class="info">Container: {{ hostname }}</p>
    </div>
</body>
</html>
'''

@app.route('/', methods=['GET', 'POST'])
def index():
    voter_id = request.cookies.get('voter_id', socket.gethostname())
    if request.method == 'POST':
        vote = request.form.get('vote')
        data = json.dumps({'voter_id': voter_id, 'vote': vote})
        r.rpush('votes', data)

    resp = make_response(render_template_string(TEMPLATE, hostname=socket.gethostname()))
    resp.set_cookie('voter_id', voter_id)
    return resp

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)
