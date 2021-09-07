# movies.models
from django.db import models
# Create your models here.
class Movie(models.Model):
    id = models.IntegerField(primary_key=True)
    title = models.CharField(max_length=128)
    keyword = models.CharField(max_length=4000)
    Tagline = models.TextField(default='N/A')
    Overview = models.TextField(default='N/A')
    Vote_average = models.DecimalField(max_digits=5, decimal_places=3, default='0.0')
    Cover = models.CharField(max_length=200)
    #year = models.IntegerField(null=True)
    #summary = models.TextField(max_length=5000,null=True)
    #poster_url = models.URLField(blank=True, null=True)
    #slug = models.SlugField(
    #            max_length=50, null=True,blank =True, unique=True)
    
    # @classmethod
    # def import_records(cls, record_list):
    #     for record in record_list:
    #         # create record if id is not exist
    #         if not Movie.objects.filter(id=record.get("id")).exists():
    #             new_movie = cls.objects.create(**record)
    #         else:
    #             print(f"Id:{record.get('id')} is already exist.")
    #     print("Import operation done successfully")


    class Meta:
        ordering = ["-title"]
        db_table = 'movie'
    def __str__(self):
        return self.title



    