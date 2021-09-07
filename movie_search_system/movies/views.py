# Create your views here.

from django.shortcuts import render
from .models import Movie
from rest_framework.views import APIView
from rest_framework.response import Response
from . serializer import *


# Create your views here.

def display(request):
	st=Movie.objects.all() # Collect all records from table
	return render(request,'display.html',{'st':st})

class MovieView(APIView):
	#id = models.IntegerField(primary_key=True)
	#title = models.CharField(max_length=128)
	#keywords = models.CharField(max_length=4000)
	serializer_class = MovieSerializer

	def get(self, request):
		#detail = [ {"name": detail.id,"detail": detail.keywords}
		#for detail in React.objects.all()]
		st = Movie.objects.all()
		# rows = [ {"id": mov.id,"title": mov.title,"keyword": mov.keyword, "Tagline": mov.Tagline, 
		# 		"Overview": mov.Overview, "Vote_average": mov.Vote_average, "Cover":mov.Cover}
		# 		for mov in Movie.objects.all()[0:20]]
		rows = [ {"id": mov.id,"title": mov.title,"keyword": mov.keyword, "Tagline": mov.Tagline, 
				"Overview": mov.Overview, "Vote_average": mov.Vote_average, "Cover":mov.Cover}
				for mov in Movie.objects.all()]
		#for movie in st:
			#detail = [ {"ID": movie.id,"Keywords": movie.keywords}
		return Response(rows)

	def post(self, request):
		serializer = MovieSerializer(data=request.data)
		if serializer.is_valid(raise_exception=True):
			serializer.save()
			return  Response(serializer.data)
