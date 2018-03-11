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

data = get_docx_text(r"C:\Users\Cirpian\desktop\UMF data\Fizica optica.docx")
root = etree.Element('root')
id_number = 1
for i in data:
	# create XML 
	print(i)
	
	# another child with text

	questions = etree.Element('questions')
	root.append(questions)
	'''
	child = etree.Element('id')
	child.text = str(id_number)
	id_number = id_number + 1
	questions.append(child)
	'''
	child = etree.Element('category')
	child.text = "fizica"
	questions.append(child)

	child = etree.Element('subcategory')
	child.text = "fizica optica"
	questions.append(child)

	child = etree.Element('year')
	child.text = "2017"
	questions.append(child)

	child = etree.Element('question')
	child.text = i['q']
	questions.append(child)

	child = etree.Element('ans1')
	child.text = i[1]
	questions.append(child)

	child = etree.Element('ans2')
	child.text = i[2]
	questions.append(child)

	child = etree.Element('ans3')
	child.text = i[3]
	questions.append(child)

	child = etree.Element('ans4')
	child.text = i[4]
	questions.append(child)

	child = etree.Element('ans5')
	child.text = i[5]
	questions.append(child)

	child = etree.Element('rightans')
	child.text = i[6]
	questions.append(child)



s = etree.tostring(root, encoding='UTF-8', pretty_print=True, xml_declaration=True)

print(s.decode())
f1=open('data.xml', 'w', encoding="utf-8")
f1.write(s.decode())
f1.close()