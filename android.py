from flask import Flask, request, jsonify
import string
from collections import Counter
from nltk.corpus import stopwords
from nltk.sentiment.vader import SentimentIntensityAnalyzer
from nltk.stem import WordNetLemmatizer
from nltk.tokenize import word_tokenize

app = Flask(__name__)


# root
@app.route("/")
def index():
    return "This is root!!!!"


# GET
@app.route('/users/<user>')
def hello_user(user):
    return "Hello %s!" % user

#192.168.199.1
# POST
@app.route('/api/post_some_data', methods=['POST'])
def get_text_prediction():
    json = request.get_json()
    print(json)
    if len(json['data']) == None:
        # print(json['data'])
        return jsonify('invalid input')

    else:
        text = json['data'].lower()
        lower_case = text.lower()
        cleaned_text = lower_case.translate(str.maketrans('', '', string.punctuation))
        res = sentiment_analyse(cleaned_text)
        return jsonify(res)

def sentiment_analyse(sentiment_text):
    score = SentimentIntensityAnalyzer().polarity_scores(sentiment_text)
    if score['neg'] > score['pos']:
        return 'Negative'
    elif score['neg'] < score['pos']:
        return 'Positive'
    else:
        return 'Neutral'

if __name__ == '__main__':
    app.run(debug=True,host = '0.0.0.0',port = '5000')