try:
    from xml.etree.cElementTree import XML
except ImportError:
    from xml.etree.ElementTree import XML
import zipfile

import re
import dicttoxml
from lxml import etree

WORD_NAMESPACE = '{http://schemas.openxmlformats.org/wordprocessingml/2006/main}'
PARA = WORD_NAMESPACE + 'p'
TEXT = WORD_NAMESPACE + 't'

def get_docx_text(path):
    """
    Take the path of a docx file as argument, return the text in unicode.
    """
    document = zipfile.ZipFile(path)
    xml_content = document.read('word/document.xml')
    document.close()
    tree = XML(xml_content)

    data = list()
    question = dict()
    paragraphs = []
    for paragraph in tree.getiterator(PARA):
        texts = [node.text
                 for node in paragraph.getiterator(TEXT)
                 if node.text]
        if texts:
            paragraphs.append(''.join(texts))
            print(texts)
            print("------")
            if re.match('[A-Z]',texts[0]):
            	question[count] = ''.join(texts[1:])
            	count = count + 1
            elif re.match('[0-9]*',texts[0]):
            	question['q'] = ''.join(texts[1:])
            	count = 1
            if count == 7:
            	data.append(question.copy())


    return data

data = get_docx_text(r"C:\Users\Cirpian\desktop\UMF data\Chimie.docx")

root = etree.Element('root')
id_number = 1

index = 1;
for i in data:
	# create XML 
	var = " String[] chim" + str(index) + " = new String[]{\""+i['q']+"\",\""+i[1]+"\",\""+i[2]+"\",\""+i[3]+"\",\""+i[4]+"\",\""+i[5]+"\",\""+i[6]+"\"};"
	print(var)
	index = index + 1
	


