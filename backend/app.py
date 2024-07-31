import uuid

from flask import Flask, request, jsonify

app = Flask(__name__)


def md5(s):
    import hashlib
    return hashlib.md5(s.encode('utf-8')).hexdigest()


@app.before_request
def before_request():
    ts = request.headers.get('ts')
    xs = request.headers.get('xs')
    if ts and xs and xs == md5(ts + 'magic'):
        return
    else:
        app.logger.error('ts validation failed')
        return jsonify({'code': 1, 'msg': 'ts validation failed'})


@app.route('/login', methods=['POST'])
def login():
    username = request.form.get('username')
    password = request.form.get('password')
    sign = request.form.get('sign')
    if sign != md5(username + password + "magic"):
        app.logger.error('sign validation failed')
        return jsonify({'code': 1, 'msg': 'sign validation failed'})

    if username == 'admin' and password == md5('123456'):
        return jsonify({'code': 0, 'msg': 'success', 'token': str(uuid.uuid4())})

    app.logger.error('wrong username or password')
    return jsonify({'code': 1, 'msg': 'wrong username or password'})


app.run(host='0.0.0.0', port=5000)
